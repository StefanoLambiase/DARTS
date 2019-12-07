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
import main.testSmellDetection.bean.PsiClassBean;
import main.testSmellDetection.bean.PsiMethodBean;

import java.util.*;

public abstract class ConverterUtilities {

    /**
     * Restituisce un array contenenti tutte le classi del progetto in esame sotto forma di PsiClass.
     * @param myProject il progetto in esame.
     * @return un array contenente tutte le classi del progetto in esame.
     */
    public static ArrayList<PsiClassBean> getClassesFromPackages(Project myProject){
        System.out.println("############ Sono ConverterUtilities. Inizio la conversione del progetto. ################");
        ArrayList<PsiClassBean> classBeans = new ArrayList<>();

        ArrayList<PsiPackage> packages = getPackages(myProject);
        for(PsiPackage psiPackage : packages){
            recursiveResearch(psiPackage, classBeans);
        }
        System.out.println("############ Sono ConverterUtilities. Ecco le classi trovate. ################\n");
        for(PsiClassBean psiClassBean : classBeans){
            System.out.println("\nCLASSE TROVATA: " + psiClassBean.getPsiClass().getName() + " ################################");
            System.out.println("   PACKAGE: " + psiClassBean.getPsiPackage());
            System.out.println("\n   VARIABILI BLOBALI");
            ArrayList<PsiVariable> instanceVariables = PsiTestSmellUtilities.getAllInstanceVariable(psiClassBean.getPsiClass());
            for(PsiVariable var : instanceVariables){
                System.out.println("\n" + var.getTypeElement().getText());
            }
            System.out.println("\n   METODI");
            ArrayList<PsiMethodBean> psiMethodBeans = getMethodFromClass(psiClassBean.getPsiClass());

            psiClassBean.setPsiMethodBeans(psiMethodBeans);

            for(PsiMethodBean m : psiMethodBeans){
                System.out.println("\n" + m.toString());
                ArrayList<PsiMethodCallExpression> methodCallExpressions = PsiTestSmellUtilities.getAllCalledMethods(m.getPsiMethod());
                if(methodCallExpressions.size() > 0){
                    for(PsiMethodCallExpression call : methodCallExpressions) {
                        PsiReference reference = call.getMethodExpression().getReference();
                        if(reference != null){
                            PsiElement element = reference.resolve();
                            System.out.println("   reference: " + reference.getElement().toString());
                            System.out.println("   Object: " + element.toString() + "\n");
                            //System.out.println("\n Metodo chiamato: ");
                        }
                    }
                }
            }
            /*
            psiClass.accept(new JavaRecursiveElementVisitor() {
                @Override
                public void visitReferenceExpression(PsiReferenceExpression expression) {
                    super.visitReferenceExpression(expression);
                    System.out.println("Found a reference at offset " + expression.getTextRange().getStartOffset() + "Name: " + expression.getText());
                }

                @Override
                public void visitLocalVariable(PsiLocalVariable variable) {
                    super.visitLocalVariable(variable);
                    System.out.println("Found a variable at offset " + variable.getTextRange().getStartOffset() + ", Name: " + variable.getName());
                }
            });

            System.out.println("\n   METODI:\n");
            for(PsiMethod psiMethod : psiClass.getAllMethods()){
                String s = psiMethod.getName();
                if(!s.equals("Object") &&
                        !s.equals("registerNatives") &&
                        !s.equals("getClass") &&
                        !s.equals("hashCode") &&
                        !s.equals("equals") &&
                        !s.equals("clone") &&
                        !s.equals("toString") &&
                        !s.equals("notify") &&
                        !s.equals("notifyAll") &&
                        !s.equals("wait") &&
                        !s.equals("finalize")){
                    System.out.println(psiMethod.getName());
                    if(psiMethod.getBody() != null) {
                        System.out.println(psiMethod.getBody().getText() + "\n");

                        System.out.println("\n   VARIABILI DI INSTANZA INIZIALIZZATE\n");
                        ArrayList<PsiVariable> varInits = PsiTestSmellUtilities.getAllInstanceVariableInit(psiMethod, instanceVariables);
                        for(PsiVariable psiVariable : varInits){
                            System.out.println(psiVariable.toString() + "\n");
                        }
                        System.out.println("\n   VARIABILI DI INSTANZA USATE\n");
                        ArrayList<PsiVariable> varUses = PsiTestSmellUtilities.getAllInstanceVariableUses(psiMethod, instanceVariables);
                        for(PsiVariable psiVariable : varUses){
                            System.out.println(psiVariable.toString() + "\n");
                        }
                    }
                }
            }

             */
        }
        return classBeans;
    }

    /**
     * Metodo usato per ottenere la lista dei metodi di una classe con alcune informazioni aggiuntive.
     * @param psiClass la classe in esame.
     * @return la lista di metodi se ce ne sono, null altrimenti.
     */
    private static ArrayList<PsiMethodBean> getMethodFromClass(PsiClass psiClass){
        ArrayList<PsiMethodBean> methodBeans = new ArrayList<>();
        ArrayList<PsiVariable> instanceVariables = PsiTestSmellUtilities.getAllInstanceVariable(psiClass);

        for(PsiMethod psiMethod : psiClass.getAllMethods()){
            if(psiMethod.getBody() != null) {
                ArrayList<PsiVariable> varInits = PsiTestSmellUtilities.getAllInstanceVariableInit(psiMethod, instanceVariables);
                ArrayList<PsiVariable> varUses = PsiTestSmellUtilities.getAllInstanceVariableUses(psiMethod, instanceVariables);
                ArrayList<PsiMethodCallExpression> methodCalls = PsiTestSmellUtilities.getAllCalledMethods(psiMethod);

                PsiMethodBean mb = new PsiMethodBean(psiMethod, psiClass, varInits, varUses, methodCalls);
                methodBeans.add(mb);
            }
        }
        return methodBeans;
    }


    /* ######################################################## METODI DI SUPPORTO ########################################################## */
    public static ArrayList<PsiPackage> getPackages(Project myProject) {
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

    private static void recursiveResearch(PsiPackage psiPackage, ArrayList<PsiClassBean> classes){
        //Parte per le classi
        System.out.println("ITERAZIONE RICORSIVA su Package: " + psiPackage.getName() + "   ##############################\n");
        PsiClass[] innerClasses = psiPackage.getClasses();
        if(innerClasses.length == 0){
            // DO NOTHING
        } else {
            for(PsiClass psiClass : innerClasses){
                System.out.println(" Classe: " + psiClass.getName() + "\n");
                PsiClassBean psiClassBean = new PsiClassBean(psiClass, psiPackage);
                classes.add(psiClassBean);
            }
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
