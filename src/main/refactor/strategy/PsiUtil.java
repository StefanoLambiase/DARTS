package main.refactor.strategy;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;

public class PsiUtil {

    /**
     * Converte un MethodBean in PsiMethod
     *
     * @param methodBean da convertire
     * @return psi del metodo del bean
     */
    /*public static PsiMethod getPsi(MethodBean methodBean, Project project, PsiClass psiClass) {
        final PsiMethod[] foundPsiMethod = new PsiMethod[1];

        String methodName = methodBean.getName().substring(methodBean.getName().lastIndexOf('.') + 1);
        foundPsiMethod[0] = psiClass.findMethodsByName(methodName, true)[0];

        return foundPsiMethod[0];
    }*/

    /**
     * Converte un ClassBean in PsiClass
     *
     * @param classBean da convertire
     * @return psi della classe del bean
     */
   /* public static PsiClass getPsi(ClassBean classBean, Project project) {

        System.out.println("inside getPsi" + project.toString());
        String aPackage = classBean.getBelongingPackage();
        String name = classBean.getName();

        final JavaPsiFacade facade = JavaPsiFacade.getInstance(project);
        final PsiClass actualClass = facade.findClass(aPackage+"."+name, GlobalSearchScope.allScope(project));

        return actualClass;
    }
*/
}
