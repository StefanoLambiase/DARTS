package main.contextualAnalysis.messageChecker;

public abstract class StringChecker {
    public static boolean isBugFixingMessage(String commitMsg){
        String candidate1 = "bug";
        String candidate2 = "buggy";
        String candidate3 = "bugged";
        String candidate4 = "refactoring";
        String candidate5 = "remove";
        String candidate6 = "fix";
        String candidate7 = "fixing";
        String candidate8 = "error";
        String candidate9 = "fault";
        String candidate10 = "remove";

        if(commitMsg.toLowerCase().contains(candidate1)){
            return true;
        } else if(commitMsg.toLowerCase().contains(candidate2)){
            return true;
        }else if(commitMsg.toLowerCase().contains(candidate3)){
            return true;
        }else if(commitMsg.toLowerCase().contains(candidate4)){
            return true;
        }else if(commitMsg.toLowerCase().contains(candidate5)){
            return true;
        }else if(commitMsg.toLowerCase().contains(candidate6)){
            return true;
        }else if(commitMsg.toLowerCase().contains(candidate7)){
            return true;
        }else if(commitMsg.toLowerCase().contains(candidate8)){
            return true;
        }else if(commitMsg.toLowerCase().contains(candidate9)){
            return true;
        }else if(commitMsg.toLowerCase().contains(candidate10)){
            return true;
        }

        return false;
    }
}
