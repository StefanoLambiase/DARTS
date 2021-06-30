package testSmellDetection.textualRules;

import com.harukizaemon.simian.*;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.testCodeDuplication.MethodWithTestCodeDuplication;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestCodeDuplicationTextual {
    public static ArrayList<MethodWithTestCodeDuplication> checkMethodsThatCauseTestCodeDuplication(PsiClassBean testClass) {
        ArrayList<MethodWithTestCodeDuplication> methodsWithTestCodeDuplication = new ArrayList<>();
        for (PsiMethodBean psiMethodBeanInside : testClass.getPsiMethodBeans()) {
            String methodName = psiMethodBeanInside.getPsiMethod().getName();
            if (!methodName.equals(testClass.getPsiClass().getName()) &&
                    !methodName.toLowerCase().equals("setup") &&
                    !methodName.toLowerCase().equals("teardown")) {

                class MyAuditListener implements AuditListener {
                    private Map<String, ArrayList<Block>> blocksOfDuplicatedCode = new HashMap<>();
                    private int startLine;
                    private int endLine;
                    private String currentSet;

                    public MyAuditListener(int startLine, int endLine) {
                        super();
                        this.startLine = startLine;
                        this.endLine = endLine;
                    }

                    @Override
                    public void startCheck(Options options) {
                    }

                    @Override
                    public void fileProcessed(SourceFile sourceFile) {
                    }

                    @Override
                    public void startSet(int i, String s) {
                        currentSet = s;
                        blocksOfDuplicatedCode.put(currentSet, new ArrayList<Block>());
                    }

                    @Override
                    public void block(Block block) {
                        if (block.getStartLineNumber() >= startLine && block.getEndLineNumber() <= endLine) {
                            blocksOfDuplicatedCode.get(currentSet).add(block);
                        }
                    }

                    @Override
                    public void endSet(String s) {
                    }

                    @Override
                    public void endCheck(CheckSummary checkSummary) {
                    }

                    @Override
                    public void error(File file, Throwable throwable) {
                    }

                    public Map<String, ArrayList<Block>> getBlocksOfDuplicatedCode() {
                        return blocksOfDuplicatedCode;
                    }

                }

                // Ottengo il progetto corrente
                Project[] projects = ProjectManager.getInstance().getOpenProjects();
                Project activeProject = null;
                for (Project project : projects) {
                    Window window = WindowManager.getInstance().suggestParentWindow(project);
                    if (window != null && window.isActive()) {
                        activeProject = project;
                    }
                }

                // Ottengo il file corrente ed il riferimento al documento
                PsiDocumentManager documentManager = PsiDocumentManager.getInstance(activeProject);
                PsiFile file = testClass.getPsiClass().getContainingFile();
                Document document = documentManager.getDocument(file);

                // Calcolo la linea di inizio del metodo e quella di fine
                int startOffset = psiMethodBeanInside.getPsiMethod().getBody().getFirstBodyElement().getTextOffset();
                int endOffset = psiMethodBeanInside.getPsiMethod().getBody().getLastBodyElement().getTextOffset();
                int startLineNumber = document.getLineNumber(startOffset);
                int endLineNumber = document.getLineNumber(endOffset);

                MyAuditListener auditListener = new MyAuditListener(startLineNumber, endLineNumber);

                Options options = new Options();
                options.setThreshold(3);
                options.setOption(Option.IGNORE_STRINGS, true);
                options.setOption(Option.IGNORE_VARIABLE_NAMES, true);

                Checker checker = new Checker(auditListener, options);

                StreamLoader streamLoader = new StreamLoader(checker);

                FileLoader fileLoader = new FileLoader(streamLoader);

                fileLoader.load(file.getVirtualFile().getPath());

                checker.check();

                Map<String, ArrayList<Block>> codeBlocks = auditListener.getBlocksOfDuplicatedCode();

                if (!auditListener.getBlocksOfDuplicatedCode().isEmpty()) {
                    boolean isEmpty = false;
                    for (Map.Entry<String, ArrayList<Block>> entryBlock : auditListener.getBlocksOfDuplicatedCode().entrySet()) {
                        if (entryBlock.getValue().isEmpty()) {
                            isEmpty = true;
                            break;
                        }
                    }
                    if (!isEmpty)
                        methodsWithTestCodeDuplication.add(new MethodWithTestCodeDuplication(psiMethodBeanInside, auditListener.getBlocksOfDuplicatedCode()));
                }

            }
        }
        if (methodsWithTestCodeDuplication.isEmpty()) {
            return null;
        } else {
            return methodsWithTestCodeDuplication;
        }
    }
}
