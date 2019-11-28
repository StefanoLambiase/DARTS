package main.utility;

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

public abstract class ConverterUtilities {

    /**
     * Restituisce un array contenenti tutte le classi del progetto in esame sotto forma di PsiClass.
     * @param myProject il progetto in esame.
     * @return un array contenente tutte le classi del progetto in esame.
     */
    public static ArrayList<PsiClass> getClassesFromPackages(Project myProject){
        System.out.println("############ Sono ConverterUtilities. Inizio la conversione del progetto. ################");
        ArrayList<PsiClass> classes = new ArrayList<>();

        ArrayList<PsiPackage> packages = getPackages(myProject);
        for(PsiPackage psiPackage : packages){
            recursiveResearch(psiPackage, classes);
        }

        System.out.println("############ Sono ConverterUtilities. Ecco le classi trovate. ################\n");
        for(PsiClass psiClass : classes){
            System.out.println("CLASSE TROVATA: " + psiClass.getName() + "\n");
            System.out.println(" METODI:\n");
            for(PsiMethod psiMethod : psiClass.getAllMethods()){
                System.out.println(psiMethod.getName());
                if(psiMethod.getBody() != null)
                    System.out.println(psiMethod.getBody().getText() + "\n");
            }
        }

        return classes;
    }


    /* METODI DI SUPPORTO */
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
            System.out.println("PsiDirectory: " + directory.getName());
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
        System.out.println("#################################################################################################################################################\n");
        for(PsiPackage psiPackage : topLevelPackages){
            System.out.println("Pacchetto trovato: " + psiPackage.getName() + "\n");
        }
        return new ArrayList<PsiPackage>(topLevelPackages);
    }


    private static void recursiveResearch(PsiPackage psiPackage, ArrayList<PsiClass> classes){
        //Parte per le classi
        System.out.println("ITERAZIONE RICORSIVA su Package: " + psiPackage.getName() + "   ##############################\n");
        PsiClass[] innerClasses = psiPackage.getClasses();
        if(innerClasses.length == 0){
            // DO NOTHING
        } else {
            for(PsiClass psiClass : innerClasses){
                System.out.println(" Classe: " + psiClass.getName() + "\n");
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
