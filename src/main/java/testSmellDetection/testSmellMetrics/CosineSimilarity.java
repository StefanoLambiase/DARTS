package testSmellDetection.testSmellMetrics;

import utility.FileUtility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CosineSimilarity {
    private File stopwordList;

    public double computeSimilarity(String[] pDocumentOne, String[] pDocumentTwo) throws IOException {
        stopwordList = File.createTempFile("fileForLackOfCohesionDetection", null);
        stopwordList.deleteOnExit();

        PrintWriter pw = new PrintWriter(stopwordList);
        pw.println("abstract");pw.println("double");pw.println("int");pw.println("static");pw.println("boolean");pw.println("else");pw.println("interface");pw.println("super");pw.println("break");pw.println("extends");pw.println("long");pw.println("switch");pw.println("byte");pw.println("final");pw.println("native");pw.println("synchronized");pw.println("case");pw.println("finally");pw.println("new");pw.println("this");pw.println("catch");pw.println("float");pw.println("null");pw.println("throw");pw.println("char");pw.println("for");pw.println("package");pw.println("throws");pw.println("class");pw.println("goto");pw.println("private");pw.println("transient");pw.println("const");pw.println("if");pw.println("protected");pw.println("try");pw.println("continue");pw.println("implements");pw.println("public");pw.println("void");pw.println("default");pw.println("import");pw.println("return");pw.println("volatile");pw.println("do");pw.println("instanceof");pw.println("short");pw.println("while");pw.println("abstract");pw.println("boolean");pw.println("break");pw.println("byte");pw.println("case");pw.println("catch");pw.println("class");pw.println("const");pw.println("continue");pw.println("default");pw.println("do");pw.println("double");pw.println("false");pw.println("final");pw.println("finally");pw.println("if");pw.println("implements");pw.println("import");pw.println("instanceof");pw.println("int");pw.println("interface");pw.println("new");pw.println("native");pw.println("private");pw.println("protected");pw.println("public");pw.println("return");pw.println("short");pw.println("static");pw.println("super");pw.println("this");pw.println("throw");pw.println("throws");pw.println("transient");pw.println("true");pw.println("void");pw.println("volatile");pw.println("your");pw.println("yours");pw.println("yourself");pw.println("yourselves");pw.println("you");pw.println("yond");pw.println("yonder");pw.println("yon");pw.println("ye");pw.println("yet");pw.println("z");pw.println("zillion");pw.println("j");pw.println("u");pw.println("umpteen");pw.println("usually");pw.println("us");pw.println("uponed");pw.println("upons");pw.println("uponing");pw.println("upon");pw.println("ups");pw.println("upping");pw.println("upped");pw.println("up");pw.println("unto");pw.println("until");pw.println("unless");pw.println("unlike");pw.println("unliker");pw.println("unlikest");pw.println("under");pw.println("underneath");pw.println("use");pw.println("used");pw.println("usedest");pw.println("r");pw.println("rath");pw.println("rather");pw.println("rathest");pw.println("rathe");pw.println("re");pw.println("relate");pw.println("related");pw.println("relatively");pw.println("regarding");pw.println("really");pw.println("res");pw.println("respecting");pw.println("respectively");pw.println("q");pw.println("quite");pw.println("que");pw.println("qua");pw.println("n");pw.println("neither");pw.println("neaths");pw.println("neath");pw.println("nethe");pw.println("nethermost");pw.println("necessary");pw.println("necessariest");pw.println("necessarier");pw.println("never");pw.println("nevertheless");pw.println("nigh");pw.println("nighest");pw.println("nigher");pw.println("nine");pw.println("noone");pw.println("nobody");pw.println("nobodies");pw.println("nowhere");pw.println("nowheres");pw.println("no");pw.println("noes");pw.println("nor");pw.println("nos");pw.println("no-one");pw.println("none");pw.println("not");pw.println("notwithstanding");pw.println("nothings");pw.println("nothing");pw.println("nathless");pw.println("natheless");pw.println("t");pw.println("ten");pw.println("tills");pw.println("till");pw.println("tilled");pw.println("tilling");pw.println("to");pw.println("towards");pw.println("toward");pw.println("towardest");pw.println("towarder");pw.println("together");pw.println("too");pw.println("thy");pw.println("thyself");pw.println("thus");pw.println("than");pw.println("that");pw.println("those");pw.println("thou");pw.println("though");pw.println("thous");pw.println("thouses");pw.println("thoroughest");pw.println("thorougher");pw.println("thorough");pw.println("thoroughly");pw.println("thru");pw.println("thruer");pw.println("thruest");pw.println("thro");pw.println("through");pw.println("throughout");pw.println("throughest");pw.println("througher");pw.println("thine");pw.println("this");pw.println("thises");pw.println("they");pw.println("thee");pw.println("the");pw.println("then");pw.println("thence");pw.println("thenest");pw.println("thener");pw.println("them");pw.println("themselves");pw.println("these");pw.println("therer");pw.println("there");pw.println("thereby");pw.println("therest");pw.println("thereafter");pw.println("therein");pw.println("thereupon");pw.println("therefore");pw.println("their");pw.println("theirs");pw.println("thing");pw.println("things");pw.println("three");pw.println("two");pw.println("o");pw.println("oh");pw.println("owt");pw.println("owning");pw.println("owned");pw.println("own");pw.println("owns");pw.println("others");pw.println("other");pw.println("otherwise");pw.println("otherwisest");pw.println("otherwiser");pw.println("of");pw.println("often");pw.println("oftener");pw.println("oftenest");pw.println("off");pw.println("offs");pw.println("offest");pw.println("one");pw.println("ought");pw.println("oughts");pw.println("our");pw.println("ours");pw.println("ourselves");pw.println("ourself");pw.println("outest");pw.println("outed");pw.println("outwith");pw.println("outs");pw.println("outside");pw.println("over");pw.println("overallest");pw.println("overaller");pw.println("overalls");pw.println("overall");pw.println("overs");pw.println("or");pw.println("orer");pw.println("orest");pw.println("on");pw.println("oneself");pw.println("onest");pw.println("ons");pw.println("onto");pw.println("a");pw.println("atween");pw.println("at");pw.println("athwart");pw.println("atop");pw.println("afore");pw.println("afterward");pw.println("afterwards");pw.println("after");pw.println("afterest");pw.println("afterer");pw.println("ain");pw.println("an");pw.println("any");pw.println("anything");pw.println("anybody");pw.println("anyone");pw.println("anyhow");pw.println("anywhere");pw.println("anent");pw.println("anear");pw.println("and");pw.println("andor");pw.println("another");pw.println("around");pw.println("ares");pw.println("are");pw.println("aest");pw.println("aer");pw.println("against");pw.println("again");pw.println("accordingly");pw.println("abaft");pw.println("abafter");pw.println("abaftest");pw.println("abovest");pw.println("above");pw.println("abover");pw.println("abouter");pw.println("aboutest");pw.println("about");pw.println("aid");pw.println("amidst");pw.println("amid");pw.println("among");pw.println("amongst");pw.println("apartest");pw.println("aparter");pw.println("apart");pw.println("appeared");pw.println("appears");pw.println("appear");pw.println("appearing");pw.println("appropriating");pw.println("appropriate");pw.println("appropriatest");pw.println("appropriates");pw.println("appropriater");pw.println("appropriated");pw.println("already");pw.println("always");pw.println("also");pw.println("along");pw.println("alongside");pw.println("although");pw.println("almost");pw.println("all");pw.println("allest");pw.println("aller");pw.println("allyou");pw.println("alls");pw.println("albeit");pw.println("awfully");pw.println("as");pw.println("aside");pw.println("asides");pw.println("aslant");pw.println("ases");pw.println("astrider");pw.println("astride");pw.println("astridest");pw.println("astraddlest");pw.println("astraddler");pw.println("astraddle");pw.println("availablest");pw.println("availabler");pw.println("available");pw.println("aughts");pw.println("aught");pw.println("vs");pw.println("v");pw.println("variousest");pw.println("variouser");pw.println("various");pw.println("via");pw.println("vis-a-vis");pw.println("vis-a-viser");pw.println("vis-a-visest");pw.println("viz");pw.println("very");pw.println("veriest");pw.println("verier");pw.println("versus");pw.println("k");pw.println("g");pw.println("go");pw.println("gone");pw.println("good");pw.println("got");pw.println("gotta");pw.println("gotten");pw.println("get");pw.println("gets");pw.println("getting");pw.println("b");pw.println("by");pw.println("byandby");pw.println("by-and-by");pw.println("bist");pw.println("both");pw.println("but");pw.println("buts");pw.println("be");pw.println("beyond");pw.println("because");pw.println("became");pw.println("becomes");pw.println("become");pw.println("becoming");pw.println("becomings");pw.println("becominger");pw.println("becomingest");pw.println("behind");pw.println("behinds");pw.println("before");pw.println("beforehand");pw.println("beforehandest");pw.println("beforehander");pw.println("bettered");pw.println("betters");pw.println("better");pw.println("bettering");pw.println("betwixt");pw.println("between");pw.println("beneath");pw.println("been");pw.println("below");pw.println("besides");pw.println("beside");pw.println("m");pw.println("my");pw.println("myself");pw.println("mucher");pw.println("muchest");pw.println("much");pw.println("must");pw.println("musts");pw.println("musths");pw.println("musth");pw.println("main");pw.println("make");pw.println("mayest");pw.println("many");pw.println("mauger");pw.println("maugre");pw.println("me");pw.println("meanwhiles");pw.println("meanwhile");pw.println("mostly");pw.println("most");pw.println("moreover");pw.println("more");pw.println("might");pw.println("mights");pw.println("midst");pw.println("midsts");pw.println("h");pw.println("huh");pw.println("humph");pw.println("he");pw.println("hers");pw.println("herself");pw.println("her");pw.println("hereby");pw.println("herein");pw.println("hereafters");pw.println("hereafter");pw.println("hereupon");pw.println("hence");pw.println("hadst");pw.println("had");pw.println("having");pw.println("haves");pw.println("have");pw.println("has");pw.println("hast");pw.println("hardly");pw.println("hae");pw.println("hath");pw.println("him");pw.println("himself");pw.println("hither");pw.println("hitherest");pw.println("hitherer");pw.println("his");pw.println("how-do-you-do");pw.println("however");pw.println("how");pw.println("howbeit");pw.println("howdoyoudo");pw.println("hoos");pw.println("hoo");pw.println("w");pw.println("woulded");pw.println("woulding");pw.println("would");pw.println("woulds");pw.println("was");pw.println("wast");pw.println("we");pw.println("wert");pw.println("were");pw.println("with");pw.println("withal");pw.println("without");pw.println("within");pw.println("why");pw.println("what");pw.println("whatever");pw.println("whateverer");pw.println("whateverest");pw.println("whatsoeverer");pw.println("whatsoeverest");pw.println("whatsoever");pw.println("whence");pw.println("whencesoever");pw.println("whenever");pw.println("whensoever");pw.println("when");pw.println("whenas");pw.println("whether");pw.println("wheen");pw.println("whereto");pw.println("whereupon");pw.println("wherever");pw.println("whereon");pw.println("whereof");pw.println("where");pw.println("whereby");pw.println("wherewithal");pw.println("wherewith");pw.println("whereinto");pw.println("wherein");pw.println("whereafter");pw.println("whereas");pw.println("wheresoever");pw.println("wherefrom");pw.println("which");pw.println("whichever");pw.println("whichsoever");pw.println("whilst");pw.println("while");pw.println("whiles");pw.println("whithersoever");pw.println("whither");pw.println("whoever");pw.println("whosoever");pw.println("whoso");pw.println("whose");pw.println("whomever");pw.println("s");pw.println("syne");pw.println("syn");pw.println("shalling");pw.println("shall");pw.println("shalled");pw.println("shalls");pw.println("shoulding");pw.println("should");pw.println("shoulded");pw.println("shoulds");pw.println("she");pw.println("sayyid");pw.println("sayid");pw.println("said");pw.println("saider");pw.println("saidest");pw.println("same");pw.println("samest");pw.println("sames");pw.println("samer");pw.println("saved");pw.println("sans");pw.println("sanses");pw.println("sanserifs");pw.println("sanserif");pw.println("so");pw.println("soer");pw.println("soest");pw.println("sobeit");pw.println("someone");pw.println("somebody");pw.println("somehow");pw.println("some");pw.println("somewhere");pw.println("somewhat");pw.println("something");pw.println("sometimest");pw.println("sometimes");pw.println("sometimer");pw.println("sometime");pw.println("several");pw.println("severaler");pw.println("severalest");pw.println("serious");pw.println("seriousest");pw.println("seriouser");pw.println("senza");pw.println("send");pw.println("sent");pw.println("seem");pw.println("seems");pw.println("seemed");pw.println("seemingest");pw.println("seeminger");pw.println("seemings");pw.println("seven");pw.println("summat");pw.println("sups");pw.println("sup");pw.println("supping");pw.println("supped");pw.println("such");pw.println("since");pw.println("sine");pw.println("sines");pw.println("sith");pw.println("six");pw.println("stop");pw.println("stopped");pw.println("p");pw.println("plaintiff");pw.println("plenty");pw.println("plenties");pw.println("please");pw.println("pleased");pw.println("pleases");pw.println("per");pw.println("perhaps");pw.println("particulars");pw.println("particularly");pw.println("particular");pw.println("particularest");pw.println("particularer");pw.println("pro");pw.println("providing");pw.println("provides");pw.println("provided");pw.println("provide");pw.println("probably");pw.println("l");pw.println("layabout");pw.println("layabouts");pw.println("latter");pw.println("latterest");pw.println("latterer");pw.println("latterly");pw.println("latters");pw.println("lots");pw.println("lotting");pw.println("lotted");pw.println("lot");pw.println("lest");pw.println("less");pw.println("ie");pw.println("ifs");pw.println("if");pw.println("i");pw.println("itself");pw.println("its");pw.println("it");pw.println("is");pw.println("idem");pw.println("idemer");pw.println("idemest");pw.println("immediate");pw.println("immediately");pw.println("immediatest");pw.println("immediater");pw.println("inwards");pw.println("inwardest");pw.println("inwarder");pw.println("inward");pw.println("inasmuch");pw.println("into");pw.println("instead");pw.println("insofar");pw.println("indicates");pw.println("indicated");pw.println("indicate");pw.println("indicating");pw.println("indeed");pw.println("inc");pw.println("f");pw.println("fact");pw.println("facts");pw.println("fs");pw.println("figupon");pw.println("figupons");pw.println("figuponing");pw.println("figuponed");pw.println("few");pw.println("fewer");pw.println("fewest");pw.println("frae");pw.println("from");pw.println("failing");pw.println("failings");pw.println("five");pw.println("furthers");pw.println("furtherer");pw.println("furthered");pw.println("furtherest");pw.println("further");pw.println("furthering");pw.println("furthermore");pw.println("fourscore");pw.println("followthrough");pw.println("for");pw.println("forwhy");pw.println("fornenst");pw.println("formerly");pw.println("former");pw.println("formerer");pw.println("formerest");pw.println("formers");pw.println("forbye");pw.println("forby");pw.println("fore");pw.println("forever");pw.println("forer");pw.println("fores");pw.println("four");pw.println("d");pw.println("ddays");pw.println("dday");pw.println("do");pw.println("doing");pw.println("doings");pw.println("doe");pw.println("does");pw.println("doth");pw.println("downwarder");pw.println("downwardest");pw.println("downward");pw.println("downwards");pw.println("downs");pw.println("done");pw.println("doner");pw.println("dones");pw.println("donest");pw.println("dos");pw.println("dost");pw.println("did");pw.println("differentest");pw.println("differenter");pw.println("different");pw.println("describing");pw.println("describe");pw.println("describes");pw.println("described");pw.println("despiting");pw.println("despites");pw.println("despited");pw.println("despite");pw.println("during");pw.println("c");pw.println("cum");pw.println("circa");pw.println("chez");pw.println("cer");pw.println("certain");pw.println("certainest");pw.println("certainer");pw.println("cest");pw.println("canst");pw.println("cannot");pw.println("cant");pw.println("cants");pw.println("canting");pw.println("cantest");pw.println("canted");pw.println("co");pw.println("could");pw.println("couldst");pw.println("comeon");pw.println("comeons");pw.println("come-ons");pw.println("come-on");pw.println("concerning");pw.println("concerninger");pw.println("concerningest");pw.println("consequently");pw.println("considering");pw.println("e");pw.println("eg");pw.println("eight");pw.println("either");pw.println("even");pw.println("evens");pw.println("evenser");pw.println("evensest");pw.println("evened");pw.println("evenest");pw.println("ever");pw.println("everyone");pw.println("everything");pw.println("everybody");pw.println("everywhere");pw.println("every");pw.println("ere");pw.println("each");pw.println("et");pw.println("etc");pw.println("elsewhere");pw.println("else");pw.println("ex");pw.println("excepted");pw.println("excepts");pw.println("except");pw.println("excepting");pw.println("exes");pw.println("enough");pw.println("f");pw.println("0");pw.println("1");pw.println("2");pw.println("3");pw.println("4");pw.println("2009");pw.println("ad");pw.println("add");pw.println("al");pw.println("alla");pw.println("altrimenti");pw.println("amora");pw.println("appartenenti");pw.println("applic");pw.println("author");pw.println("boolean");pw.println("block");pw.println("bean");pw.println("catch");pw.println("ci");pw.println("che");pw.println("con");pw.println("connect");pw.println("copyright");pw.println("cui");pw.println("da");pw.println("dal");pw.println("dall");pw.println("dalla");pw.println("databas");pw.println("dato");pw.println("dbconnect");pw.println("default");pw.println("deg");pw.println("dei");pw.println("delete");pw.println("dell");pw.println("della");pw.println("deve");pw.println("di");pw.println("entity");pw.println("not");pw.println("found");pw.println("error");pw.println("message");pw.println("extend");pw.println("gesa");
        pw.close();

        pDocumentOne = normalize(pDocumentOne);
        pDocumentTwo = normalize(pDocumentTwo);

        Map<String, Double> featureOne = this.countOccourrences(pDocumentOne);
        Map<String, Double> featureTwo = this.countOccourrences(pDocumentTwo);

        return CosineSimilarity.computeCosineSimilarity(featureOne, featureTwo);

    }

    private Map<String, Double> countOccourrences(String[] pDocument) {
        Map<String, Double> terms = new HashMap<String, Double>();
        String[] initialWords = pDocument[1].split(" ");

        for(String word: initialWords) {

            if(! terms.containsKey(word)) {
                terms.put(word, 1.0);
            } else {
                terms.put(word, terms.get(word)+1.0);
            }
        }
        return terms;
    }

    private String[] normalize(String[] document) throws IOException {
        String stopWordListFile = FileUtility.readFile(this.stopwordList.getAbsolutePath());
        String[] stopWordList = stopWordListFile.split("\n");
        Vector<String> finalWords=new Vector<String>();

        document[1] = splitCamelCase(document[1]);
        document[1] = document[1].toLowerCase();

        document[1] = deleteAllSymbols(document[1]);

        String[] newDoc = document[1].split(" ");

        for(String s: newDoc)
            s = s.replaceAll(" ", "");

        newDoc = deleteAllWhiteSpaces(newDoc);

        for(String word: newDoc) {
            finalWords.add(word);
        }

        for(String stopWord: stopWordList) {
            for(String word: newDoc) {
                if(stopWord.equals(word)) {
                    finalWords.remove(word);
                }
            }
        }

        String[] toReturn = new String[2];

        String value="";
        for(String s: finalWords) {
            value += "" + s + " ";
        }

        toReturn[0] = document[0];
        toReturn[1] = value;

        return toReturn;
    }

    private String splitCamelCase(String s) {
        return s.replaceAll(String.format("%s|%s|%s",
                "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
                "(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
    }

    private String[] deleteAllWhiteSpaces(String[] pArray) {
        Vector<String> newVector=new Vector<String>();

        for(String s: pArray) {
            if(! s.isEmpty())
                newVector.add(s);
        }

        String[] words = new String[newVector.size()];
        for(int k=0; k<newVector.size(); k++)
            words[k] = newVector.get(k);

        return words;
    }

    private String deleteAllSymbols(String s) {
        s = s.replaceAll("\\(", " ");
        s = s.replaceAll("\\)", " ");
        s = s.replaceAll("\\[", " ");
        s = s.replaceAll("\\]", " ");
        s = s.replaceAll("\\{", " ");
        s = s.replaceAll("\\}", " ");
        s = s.replaceAll("\\=", " ");
        s = s.replaceAll("\\/", " ");
        s = s.replaceAll("\\+", " ");
        s = s.replaceAll("\\-", " ");
        s = s.replaceAll("\\*", " ");
        s = s.replaceAll("\\_", " ");
        s = s.replaceAll("\\%", " ");
        s = s.replaceAll("\\!", " ");
        s = s.replaceAll("\\;", " ");
        s = s.replaceAll("\\,", " ");
        s = s.replaceAll("\\.", " ");
        s = s.replaceAll("\"", " ");
        s = s.replaceAll("\\:", " ");
        s = s.replaceAll("\\n", " ");

        return s;
    }

    private static Double computeCosineSimilarity(Map<String, Double> firstFeatures, Map<String, Double> secondFeatures) {
        Double similarity = 0.0;
        Double sum = 0.0;        // the numerator of the cosine similarity
        Double fnorm = 0.0;        // the first part of the denominator of the cosine similarity
        Double snorm = 0.0;        // the second part of the denominator of the cosine similarity
        Set<String> fkeys = firstFeatures.keySet();
        Iterator<String> fit = fkeys.iterator();
        while (fit.hasNext()) {
            String featurename = fit.next();
            boolean containKey = secondFeatures.containsKey(featurename);
            if (containKey) {
                sum = sum + firstFeatures.get(featurename) * secondFeatures.get(featurename);
            }
        }

        fnorm = calculateNorm(firstFeatures);
        snorm = calculateNorm(secondFeatures);
        similarity = sum / (fnorm * snorm);

        return similarity;
    }

    private static Double calculateNorm(Map<String, Double> feature) {
        Double norm = 0.0;
        Set<String> keys = feature.keySet();
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String featurename = it.next();
            norm = norm + Math.pow(feature.get(featurename), 2);
        }
        return Math.sqrt(norm);
    }
}
