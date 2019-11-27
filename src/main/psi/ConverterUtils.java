package main.psi;

import com.intellij.ide.projectView.ProjectViewSettings;
import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.ide.projectView.impl.nodes.ProjectViewDirectoryHelper;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.util.containers.ContainerUtil;

import java.util.*;

public abstract class ConverterUtils {


    public static ArrayList<PsiClass> getClassesFromPackages(Project myProject){
        ArrayList<PsiClass> classes = new ArrayList<>();

        ArrayList<PsiPackage> packages = getPackages(myProject);
        for(PsiPackage psiPackage : packages){
            recursiveResearch(psiPackage, classes);
        }

        for(PsiClass psiClass : classes){
            System.out.println("\nClassi trovate: " + psiClass.getName() + "\n");
            System.out.println(" METODI");
            for(PsiMethod psiMethod : psiClass.getAllMethods()){
                System.out.println(psiMethod.getName());
                if(psiMethod.getBody() != null)
                    System.out.println(psiMethod.getBody().getText() + "\n");
            }
        }

        return classes;
    }

    private static ArrayList<PsiPackage> getPackages(Project myProject) {
        ProjectViewSettings viewSettings = new ProjectViewSettings() {
            @Override
            public boolean isShowExcludedFiles() {
                return false;
            }

            @Override
            public boolean isShowMembers() {
                return false;
            }

            @Override
            public boolean isStructureView() {
                return false;
            }

            @Override
            public boolean isShowModules() {
                return false;
            }

            @Override
            public boolean isFlattenPackages() {
                return false;
            }

            @Override
            public boolean isAbbreviatePackageNames() {
                return false;
            }

            @Override
            public boolean isHideEmptyMiddlePackages() {
                return false;
            }

            @Override
            public boolean isShowLibraryContents() {
                return false;
            }
        };

        final List<VirtualFile> sourceRoots = new ArrayList<>();
        final ProjectRootManager projectRootManager = ProjectRootManager.getInstance(myProject);
        ContainerUtil.addAll(sourceRoots, projectRootManager.getContentSourceRoots());
        for(VirtualFile vf : sourceRoots)
            System.out.println("VirtualFile: " + vf.getName() + "\n");

        final PsiManager psiManager = PsiManager.getInstance(myProject);
        final List<AbstractTreeNode> children = new ArrayList<>();
        final Set<PsiPackage> topLevelPackages = new HashSet<>();
        ArrayList<PsiClass> classes = new ArrayList<>();

        for (final VirtualFile root : sourceRoots) {
            final PsiDirectory directory = psiManager.findDirectory(root);
            System.out.println("\nPsiDirectory: " + directory.getName());
            if (directory == null) {
                continue;
            }
            final PsiPackage directoryPackage = JavaDirectoryService.getInstance().getPackage(directory);
            System.out.println("   Package: " + directoryPackage.getName());
            if (directoryPackage == null || PackageUtil.isPackageDefault(directoryPackage)) {
                // add subpackages
                final PsiDirectory[] subdirectories = directory.getSubdirectories();
                for (PsiDirectory subdirectory : subdirectories) {
                    final PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(subdirectory);
                    System.out.println("      SubPackage: " + aPackage.getName());
                    if (aPackage != null && !PackageUtil.isPackageDefault(aPackage)) {
                        // Inizio la ricerca ricorsiva
                        //recursiveResearch(aPackage, classes);
                        topLevelPackages.add(aPackage);
                    }
                }
                // add non-dir items
                children.addAll(ProjectViewDirectoryHelper.getInstance(myProject).getDirectoryChildren(directory, viewSettings, false));
            } else {
                // this is the case when a source root has package prefix assigned
                topLevelPackages.add(directoryPackage);
            }
        }
        System.out.println("#################################################################################################################################################");
        for(PsiPackage psiPackage : topLevelPackages){
            System.out.println("\nPacchetti trovati: " + psiPackage.getName() + "\n");
        }
        return new ArrayList<PsiPackage>(topLevelPackages);
    }


    private static void recursiveResearch(PsiPackage psiPackage, ArrayList<PsiClass> classes){
        //Parte per le classi
        System.out.println("\nITERAZIONE RICORSIVA su Package: " + psiPackage.getName() + "   ##############################\n");
        PsiClass[] innerClasses = psiPackage.getClasses();
        if(innerClasses.length == 0){
            // DO NOTHING
        } else {
            for(PsiClass psiClass : innerClasses){
                System.out.println(" Classe: " + psiClass.getName() + "");
            }
            classes.addAll(Arrays.asList(innerClasses));
        }
        //Parte per i package interni
        PsiPackage[] innerPackages = psiPackage.getSubPackages();
        if(innerPackages == null){
            // NON CI SONO ALTRI PACKAGE DA VISITARE
        } else {
            for(PsiPackage innerPsiPackage : innerPackages){
                recursiveResearch(innerPsiPackage, classes);
            }
        }
    }

}
