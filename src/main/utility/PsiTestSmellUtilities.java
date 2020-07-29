package main.utility;

import com.intellij.psi.*;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.PsiElementFilter;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class PsiTestSmellUtilities {

    /**
     * Metodo usato per ottenere tutte le variabili di istanza all'interno di una classe e delle sue eventuali super classi.
     * @param psiClass la classe in esame.
     * @return la lista delle variabili
     */
    public static ArrayList<PsiVariable> getAllInstanceVariable(PsiClass psiClass){
        ArrayList<PsiVariable> instanceVariables = new ArrayList<>();
        for(PsiVariable psiVariable : psiClass.getAllFields()){
            instanceVariables.add(psiVariable);
        }
        return instanceVariables;
    }

    /**
     * Metodo usato per ottenere tutte le inizializzazioni di instance variable all'interno di un metodo. Solitamente usato sul metodo di setUp della classe di test.
     * @param psiMethod il metodo in esame.
     * @param instanceVariables la lista delle variabili di istanza della classe.
     * @return la lista delle variabili inizializzate.
     */
    public static ArrayList<PsiVariable> getAllInstanceVariableInit(PsiMethod psiMethod, ArrayList<PsiVariable> instanceVariables){
        ArrayList<PsiVariable> initInstanceVariables = new ArrayList<>();
        for(PsiVariable psiVariable : instanceVariables){
            Collection<PsiReference> referenceList = ReferencesSearch.search(psiVariable).findAll();
            for(PsiReference reference : referenceList){
                //(PsiElement) reference
                PsiMethod parentMethod = PsiTreeUtil.getParentOfType(reference.getElement(), PsiMethod.class);
                if(parentMethod != null && parentMethod.getName().equals(psiMethod.getName()))
                    if(reference instanceof PsiReferenceExpression){
                        PsiAssignmentExpression psiAssignmentExpression = PsiTreeUtil.getParentOfType((PsiElement) reference, PsiAssignmentExpression.class);
                        if(psiAssignmentExpression != null)
                            if (psiAssignmentExpression.getLExpression().equals(reference))
                                initInstanceVariables.add(psiVariable);
                    }
            }
        }
        initInstanceVariables = (ArrayList<PsiVariable>) initInstanceVariables.stream().distinct().collect(Collectors.toList());
        return initInstanceVariables;
    }

    public static ArrayList<PsiVariable> getAllInstanceVariableUses(PsiMethod psiMethod, ArrayList<PsiVariable> instanceVariables){
        ArrayList<PsiVariable> usesInstanceVariables = new ArrayList<>();
        for(PsiVariable psiVariable : instanceVariables){
            Collection<PsiReference> referenceList = ReferencesSearch.search(psiVariable).findAll();
            for(PsiReference reference : referenceList){
                //(PsiElement) reference
                PsiMethod parentMethod = PsiTreeUtil.getParentOfType(reference.getElement(), PsiMethod.class);
                if(parentMethod != null && parentMethod.getName().equals(psiMethod.getName()))
                    if (reference instanceof PsiReferenceExpression) {
                        PsiAssignmentExpression psiAssignmentExpression = PsiTreeUtil.getParentOfType((PsiElement) reference, PsiAssignmentExpression.class);
                        if (psiAssignmentExpression != null)
                            if (!psiAssignmentExpression.getLExpression().equals(reference))
                                usesInstanceVariables.add(psiVariable);
                            else
                                usesInstanceVariables.add(psiVariable);
                    }
            }
        }
        usesInstanceVariables = (ArrayList<PsiVariable>) usesInstanceVariables.stream().distinct().collect(Collectors.toList());
        return usesInstanceVariables;
    }

    /**
     * Metodo usato per ottenere tutte le chiamate a metodi presenti in un metodo.
     * @param psiMethod il metodo in esame.
     * @return la lista della chiamate sotto forma di PsiMethodCallExpression.
     */
    public static ArrayList<PsiMethodCallExpression> getAllCalledMethods(PsiMethod psiMethod){
        final PsiElement[] callsElement = PsiTreeUtil.collectElements(psiMethod, new PsiElementFilter() {
            public boolean isAccepted(PsiElement e) {
                if (e instanceof PsiMethodCallExpression) {
                    return true;
                }
                return false;
            }
        });
        ArrayList<PsiMethodCallExpression> psiMethodCallExpressions = new ArrayList<>();
        for(PsiElement element : callsElement){

            PsiMethodCallExpression methodCallExpression = (PsiMethodCallExpression) element;
            psiMethodCallExpressions.add(methodCallExpression);
        }
        return psiMethodCallExpressions;
    }

    public static ArrayList<PsiMethod> getPsiMethodCalledFromMethod(PsiMethod psiMethod){
        ArrayList<PsiMethodCallExpression> methodCallExpressions = getAllCalledMethods(psiMethod);
        return getPsiMethodFromReferences(methodCallExpressions);
    }

    /**
     * Metodo usato per ottenere i metodi a cui un insieme di chiamate fa riferimento.
     * @param methodCalls l'insieme di chiamate a metodi.
     * @return la lista dei metodi chiamati sotto forma di PsiMethod.
     */
    public static ArrayList<PsiMethod> getPsiMethodFromReferences(ArrayList<PsiMethodCallExpression> methodCalls) {
        ArrayList<PsiReference> methodReferences = new ArrayList<>();
        for(PsiMethodCallExpression methodCallExpression : methodCalls)
            methodReferences.add(methodCallExpression.getMethodExpression().getReference());
        ArrayList<PsiMethod> methods = new ArrayList<>();
        for (PsiReference reference : methodReferences) {
            PsiElement element = reference.resolve();
            if(element instanceof PsiMethod)
                methods.add((PsiMethod) element);
        }
        return methods;
    }

    /**
     * Metodo usato per ottenere tutte le variabili locali in un metodo.
     * @param psiMethod il metodo in esame.
     * @return una lista delle variabili.
     */
    public static ArrayList<PsiLocalVariable> getAllLocalVariable(PsiMethod psiMethod){
        final PsiElement[] varsElement = PsiTreeUtil.collectElements(psiMethod, new PsiElementFilter() {
            public boolean isAccepted(PsiElement e) {
                if (e instanceof PsiLocalVariable) {
                    return true;
                }
                return false;
            }
        });
        ArrayList<PsiLocalVariable> vars = new ArrayList<>();
        for(PsiElement element : varsElement){
            PsiLocalVariable var = (PsiLocalVariable) element;
            vars.add(var);
        }
        return vars;
    }
}
