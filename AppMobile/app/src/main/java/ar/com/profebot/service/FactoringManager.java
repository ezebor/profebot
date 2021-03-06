package ar.com.profebot.service;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.profebot.activities.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.profebot.Models.MultipleChoiceStep;
import ar.com.profebot.activities.SolvePolynomialActivity;

public class FactoringManager extends Manager{

    public static final Integer FACTOR_COMUN = 1;
    public static final Integer CUADRATICA = 2;
    public static final Integer GAUSS = 3;

    public static final String CORRECT_OPTION = "correctOption";
    public static final String REGULAR_OPTION_1 = "regularOption1";
    public static final String REGULAR_OPTION_2 = "regularOption2";

    // (exponente, coeficiente)
    public static Map<Integer, Double> polynomialTerms;
    // (raíz, multiplicidad)
    public static Map<Double, Integer> rootsMultiplicity;
    public static List<Double> roots;
    public static String rootsFactorized;
    public static String pendingPolynomial;
    public static Double currentRoot1;
    public static Double currentRoot2;
    public static Double multiplier;
    public static String currentRootType;
    public static Boolean end;
    private static SolvePolynomialActivity context;

    public static String originalPolynomial;

    private static AlertDialog dialog;

    public static void setContext(SolvePolynomialActivity context) {
        FactoringManager.context = context;
    }

    protected Context getContext(){
        return context;
    }

    protected LayoutInflater getLayoutInflater(){
        return context.getLayoutInflater();
    }

    public static Map<Integer, Double> getPolynomialTerms() {
        return polynomialTerms;
    }

    public static void setPolynomialTerms(Map<Integer, Double> polynomialTerms) {
        FactoringManager.polynomialTerms = polynomialTerms;
        originalPolynomial = getPolynomialGeneralForm(polynomialTerms);
        roots = new ArrayList<>();
        rootsMultiplicity = new HashMap<>();
        end = false;
        multiplier = 1.0;

        // Checkear si el polinomio ingresado es factorizable
        tryToFinishingExercise();
    }

    public static MultipleChoiceStep nextStep(){
        Map<String, Integer> cases = getNextPossibleCases();
        setFactors();

        return new MultipleChoiceStep(getPolynomialToFactorize(), "", "", "", "",
                context.getString(R.string.FACTOR_COMUN), "",
                context.getString(R.string.CUADRATICA), "",
                context.getString(R.string.GAUSS), "",
                cases.get(CORRECT_OPTION), cases.get(REGULAR_OPTION_1), cases.get(REGULAR_OPTION_2), "","",
                "" );
    }

    public static Map<String, Integer> getNextPossibleCases(){
        // Veo qué casos son posibles
        Map<String, Integer> result = new HashMap<>();
        Boolean factorComunIsPossible = commonFactorIsPossible();
        Boolean quadraticIsPossible= quadraticIsPossible();
        Boolean gaussIsPossible= gaussIsPossible();

        // Fomulo opciones correctas, regulares e incorrectas

        Integer correctOption = null;
        Integer regularOption1 = null;
        Integer regularOption2 = null;

        /**
         * factor comun: 1
         * cuadrática: 2
         * gauss: 3
         */

        if(!polynomialTerms.isEmpty()){
            Integer degree = getDegree();
            Boolean hasIndependentTerm = hasIndependentTerm();
            if(!factorComunIsPossible && !quadraticIsPossible){
                if(gaussIsPossible){
                    correctOption = GAUSS;
                }
            }else if (factorComunIsPossible){
                correctOption = FACTOR_COMUN;
                if(quadraticIsPossible){
                    regularOption1 = CUADRATICA;
                    if(hasIndependentTerm){
                        regularOption2 = GAUSS;
                    }
                }else if(gaussIsPossible){ // No se puede cuadrática porque el grado es != 2
                    regularOption1 = GAUSS;
                }
            }else{
                correctOption = CUADRATICA;
                if(gaussIsPossible){
                    regularOption1 = GAUSS;
                }
            }
        }

        result.put(CORRECT_OPTION, correctOption);
        result.put(REGULAR_OPTION_1, regularOption1);
        result.put(REGULAR_OPTION_2, regularOption2);
        return result;
    }

    private static Boolean commonFactorIsPossible(){
        if(polynomialTerms.size() == 1){
            return false;
        }

        Boolean numericCommonFactor = !polynomialTerms.isEmpty() && polynomialTerms.get(getDegree()) != 1;
        Boolean variableCommonFactor = !hasIndependentTerm();

        return numericCommonFactor || variableCommonFactor;
    }

    private static Boolean quadraticIsPossible(){
        Boolean quadraticIsPossible = false;
        Boolean existsTerms = !polynomialTerms.isEmpty();
        if(existsTerms && getDegree() == 2){
            Double a = polynomialTerms.containsKey(2) ? polynomialTerms.get(2) : 0.0;
            Double b = polynomialTerms.containsKey(1) ? polynomialTerms.get(1) : 0.0;
            Double c = polynomialTerms.containsKey(0) ? polynomialTerms.get(0) : 0.0;

            Double discriminant = b * b - 4 * a * c;
            quadraticIsPossible = discriminant >= 0;
        }
        return quadraticIsPossible;
    }

    private static Boolean gaussIsPossible(){
        Boolean existsTerms = !polynomialTerms.isEmpty();
        Boolean existsAtLeastOneRoot = existsTerms && hasIndependentTerm() && !rootsOfPolynomial().isEmpty();
        return existsAtLeastOneRoot && (getDegree() > 2 || (getDegree() == 2 && quadraticIsPossible()));
    }

    public static String getPolynomialToFactorize(){
        String rootsFactorizedAux = "";
        if(!rootsFactorized.isEmpty()){
            rootsFactorizedAux = rootsFactorized;
            rootsFactorizedAux = ExpressionsManager.mapToLatexAndReplaceComparator(rootsFactorizedAux);
            rootsFactorizedAux += !pendingPolynomial.isEmpty() ? "*" : "";
        }

        String pendingPolynomialAux = "";
        if(!pendingPolynomial.isEmpty()){
            pendingPolynomialAux = ExpressionsManager.mapToLatexAndReplaceComparator(pendingPolynomial);
            pendingPolynomialAux = "\\mathbf{" + pendingPolynomialAux + "}";
        }

        return addMultiplier(rootsFactorizedAux, pendingPolynomialAux);
    }

    public static String getEquationAsLatexAfterFactorizing(){
        String rootsFactorizedAux = "";
        if(!rootsFactorized.isEmpty()){
            rootsFactorizedAux = ExpressionsManager.removeDecimals(rootsFactorized);
            System.out.println("Parte factorizada: " + rootsFactorizedAux);
            rootsFactorizedAux = ExpressionsManager.mapToLatexAndReplaceComparator(rootsFactorizedAux);
            rootsFactorizedAux = "\\mathbf{" + rootsFactorizedAux + "}" + (!pendingPolynomial.isEmpty() ? " \\cdot " : "");
        }

        String pendingPolynomialAux = "";
        if(!pendingPolynomial.isEmpty()){
            pendingPolynomialAux = ExpressionsManager.removeDecimals(pendingPolynomial);
            System.out.println("Parte pendiente: " + pendingPolynomialAux);
            pendingPolynomialAux = ExpressionsManager.mapToLatexAndReplaceComparator(pendingPolynomialAux);
        }

        String finalPolynomial = addMultiplier(rootsFactorizedAux, pendingPolynomialAux); 
        System.out.println("Polinomio final: " + finalPolynomial);
        return finalPolynomial;
    }

    private static String addMultiplier(String roots, String pending){
        String equation;
        if(multiplier != 1){
            if(roots.isEmpty()){
                equation = multiplier + "*(" + pending + ")";
            }else{
                equation = multiplier + "*" + roots + pending;
            }
        }else{
            equation = roots + pending;
        }
        return ExpressionsManager.removeDecimals(equation);
    }

    public static void setFactors(){
        // Polinomio a factorizar
        pendingPolynomial = getPolynomialGeneralForm(polynomialTerms);
        if(!roots.isEmpty() && !pendingPolynomial.isEmpty()){
            pendingPolynomial = "(" + pendingPolynomial + ")";
        }

        // Raíces ya calculadas
        StringBuilder stringBuilder = new StringBuilder("");
        for(int i = 0 ; i < roots.size() ; i++){
            if(roots.get(i) == 0){
                stringBuilder.append("x");
            }else{
                stringBuilder.append("(x");
                Double root = roots.get(i);
                stringBuilder.append(root >= 0 ? "-" : "+");
                stringBuilder.append(Math.abs(root));
                stringBuilder.append(")");
            }

            if(rootsMultiplicity.get(roots.get(i)) > 1){
                stringBuilder.append("^");
                stringBuilder.append(rootsMultiplicity.get(roots.get(i)));
            }

            if(i + 1 < roots.size()){
                stringBuilder.append("*");
            }
        }
        rootsFactorized = stringBuilder.toString();
    }

    public static String getPolynomialGeneralForm(Map<Integer, Double> terms){
        List<Integer> exponents = new ArrayList<>(terms.keySet());
        Collections.sort(exponents, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 >= o1 ? 1 : -1;
            }
        });

        StringBuilder stringBuilder = new StringBuilder("");
        Boolean firstTerm = true;
        for(Integer exponent : exponents){
            Double coefficient = terms.get(exponent);
            String operator = "+";
            if(firstTerm){
                if(coefficient > 0){
                    operator = "";
                }else{
                    operator = "-";
                }
            }else{
                if(coefficient < 0){
                    operator = "-";
                }
            }
            stringBuilder.append(operator);
            if(Math.abs(coefficient) == 1.0){
                if(exponent != 0){
                    stringBuilder.append("x");
                    if(exponent > 1){
                        stringBuilder.append("^");
                        stringBuilder.append(exponent);
                    }
                }else{
                    stringBuilder.append(Math.abs(coefficient));
                }
            }else{
                stringBuilder.append(Math.abs(coefficient));
                if(exponent != 0){
                    stringBuilder.append("*x");
                    if(exponent > 1){
                        stringBuilder.append("^");
                        stringBuilder.append(exponent);
                    }
                }
            }
            firstTerm = false;
        }
        return ExpressionsManager.removeDecimals(stringBuilder.toString());
    }

    public static void factorizeBy(Integer option){
        if(!end){
            initializeVariables();
            switch (option){
                case 1:
                    applyCommonFactor();
                    break;
                case 2:
                    applyQuadratic();
                    break;
                default:
                    applyGauss();
            }

            deleteNullTerms();

            tryToFinishingExercise();
        }
    }

    private static void deleteNullTerms(){
        List<Integer> exponents = new ArrayList<>(polynomialTerms.keySet());
        for(Integer exponent : exponents){
            if(polynomialTerms.get(exponent) == 0.0){
                polynomialTerms.remove(exponent);
            }
        }
    }

    private static void tryToFinishingExercise(){
        Boolean existsTerms = !polynomialTerms.isEmpty();

        Boolean quadraticIsPossible = quadraticIsPossible();
        Boolean gaussIsPossible = gaussIsPossible();

        if(!commonFactorIsPossible() && !quadraticIsPossible && !gaussIsPossible){
            end = true;
            Integer degree = getDegree();
            if(existsTerms){
                if(hasIndependentTerm() && degree == 1){
                    Double lastRoot = -1 * polynomialTerms.get(0);
                    addRoot(lastRoot);
                    incrementMultiplier(polynomialTerms.get(1));
                    if(currentRoot1 == null){
                        currentRoot1 = lastRoot;
                        currentRootType = getMultiplicityName(1);
                    }else{
                        currentRoot2 = lastRoot;
                    }
                    polynomialTerms.clear();
                }else if(!hasIndependentTerm()){ // Ejemplo: x^3
                    for(int i = 0 ; i < degree ; i++){
                        addRoot(0.0);
                    }
                    incrementMultiplier(polynomialTerms.get(degree));
                    currentRoot1 = 0.0;
                    currentRootType = getMultiplicityName(degree);
                    polynomialTerms.clear();
                }
            }
        }
    }

    private static void initializeVariables(){
        currentRoot1 = null;
        currentRoot2 = null;
        currentRootType = "";
    }

    private static void applyCommonFactor(){
        // Primero intento factor común numérico
        if(polynomialTerms.get(getDegree()) != 1){
            Double mainCoefficient = polynomialTerms.get(getDegree());
            for(Integer exponent : polynomialTerms.keySet()){
                polynomialTerms.put(exponent, (double) Math.round(((polynomialTerms.get(exponent) / mainCoefficient) * 100)) / 100);
            }
            incrementMultiplier(mainCoefficient);
        }else{
            Integer minExponent = Collections.min(polynomialTerms.keySet());

            List<Integer> exponents = new ArrayList<>(polynomialTerms.keySet());
            Map<Integer, Double> newPolynomial = new HashMap<>();
            for(Integer exponent : exponents){
                newPolynomial.put(exponent - minExponent, polynomialTerms.get(exponent));
            }
            polynomialTerms = newPolynomial;

            for(int i = 0 ; i < minExponent ; i++){
                addRoot(0.0);
            }

            currentRoot1 = 0.0;
            currentRootType = getMultiplicityName(minExponent);
        }

    }

    private static void applyQuadratic(){
        Double a = polynomialTerms.containsKey(2) ? polynomialTerms.get(2) : 0.0;
        Double b = polynomialTerms.containsKey(1) ? polynomialTerms.get(1) : 0.0;
        Double c = polynomialTerms.containsKey(0) ? polynomialTerms.get(0) : 0.0;

        Double discriminant = b * b - 4 * a * c;

        if(discriminant < 0){
            end = true;
        }else{
            Double root1 = (-1 * b + Math.sqrt(discriminant)) / (2 * a);
            Double root2 = (-1 * b - Math.sqrt(discriminant)) / (2 * a);

            if(root1.equals(root2)){
                currentRoot1 = root1;
                // Agrego 2 veces la misma raíz, porque es doble
                addRoot(root1);
                addRoot(root1);
            }else{
                currentRoot1 = root1;
                currentRoot2 = root2;
                addRoot(root1);
                addRoot(root2);
            }
        }

        polynomialTerms = new HashMap<>();
        if(a != 1.0){
            // Cuando se factoriza cuadrática, el resultado es: a*(x-r1)(x-r2)
            incrementMultiplier(a);
        }
        end = true;
    }

    private static void applyGauss(){
        if(hasIndependentTerm()){
            if(getDegree() == 2){
                incrementMultiplier(polynomialTerms.get(2));
            }

            List<Double> possibleRoots = rootsOfPolynomial();
            try{
                // Aplico gauss con la primer raíz que aparezca
                applyRuffini(possibleRoots.get(0));
            }catch (Exception e){
                System.out.println(e.getMessage() + " - Raíces: " + possibleRoots.toString());
            }
        }
    }

    private static List<Double> rootsOfPolynomial(){
        Double independentTerm = polynomialTerms.get(0);
        Double principalCoefficient = polynomialTerms.get(getDegree());

        List<Integer> independentTermDivisors = divisorsOf(independentTerm);
        List<Integer> principalCoefficientDivisors = divisorsOf(principalCoefficient);

        List<Double> totalRoots = new ArrayList<>();
        for(Integer independentTermDivisor : independentTermDivisors){
            for(Integer principalCoefficientDivisor : principalCoefficientDivisors){
                Double candidate = (double) independentTermDivisor / principalCoefficientDivisor;
                if(isRoot(candidate)){
                    totalRoots.add(candidate);
                }
            }
        }

        return totalRoots;
    }

    private static void applyRuffini(Double possibleRoot) throws Exception{
        // Genero el listado ordenado y completo de coeficientes

        List<Double> coefficientsSortedAndCompleted = new ArrayList<>();
        for(int exponent = getDegree() ; exponent >= 0  ; exponent--){
            Double coefficientToAdd;
            if(polynomialTerms.containsKey(exponent)){
                coefficientToAdd = polynomialTerms.get(exponent);
            }else{
                coefficientToAdd = 0.0;
            }
            coefficientsSortedAndCompleted.add(coefficientToAdd);
        }

        // Hago la división

        List<Double> quotient = new ArrayList<>();
        quotient.add((double)coefficientsSortedAndCompleted.get(0));

        for(int i = 1 ; i < coefficientsSortedAndCompleted.size() ; i++){
            Double newCoefficient =  (quotient.get(i - 1) * possibleRoot) + coefficientsSortedAndCompleted.get(i);
            quotient.add((double)Math.round(newCoefficient * 1000d) / 1000d);
        }

        // Valido el resto

        if(quotient.get(quotient.size() - 1) != 0.0){
            throw new Exception("El resto de Ruffini no es 0 --> es " + quotient.get(quotient.size() - 1));
        }

        // Genero la nueva raíz

        addRoot(possibleRoot);

        currentRoot1 = possibleRoot;
        currentRootType = getMultiplicityName(1);

        // Genero el nuevo polinomio a factorizar

        Integer currentDegree = getDegree() - 1;
        polynomialTerms.clear();
        for(int i = 0 ; i < quotient.size() - 1 ; i++){
            polynomialTerms.put(currentDegree--, quotient.get(i));
        }
    }

    private static void incrementMultiplier(Double newMultiplier){
        multiplier *= newMultiplier;
    }

    private static void addRoot(Double root){
        Double roundedRoot = (double) Math.round(root * 100) / 100;
        if(roots.contains(roundedRoot)){
            Integer multiplicity = rootsMultiplicity.get(roundedRoot);
            rootsMultiplicity.remove(roundedRoot);
            rootsMultiplicity.put(roundedRoot, multiplicity + 1);
        }else{
            roots.add(roundedRoot);
            rootsMultiplicity.put(roundedRoot, 1);
        }
    }

    private static Integer getDegree(){
        return Collections.max(polynomialTerms.keySet());
    }

    private static Boolean isRoot(Double possibleRoot){
        Double result = 0.0;
        for(Integer exponent : polynomialTerms.keySet()){
            result += polynomialTerms.get(exponent) * Math.pow(possibleRoot, exponent);
        }
        return result.equals(0.0);
    }

    private static List<Integer> divisorsOf(Double number){
        List<Integer> divisors = new ArrayList<>();
        divisors.add(1);
        divisors.add(-1);
        for(int i = 2 ; i <= number / 2 ; i++){
            if(number % i == 0){
                divisors.add(i);
                divisors.add(-1 * i);
            }
        }
        return divisors;
    }

    private static Boolean hasIndependentTerm(){
        return polynomialTerms.containsKey(0);
    }

    public static void enableSummary(){
        context.enableSummary();
    }

    private static String getMultiplicityName(Integer multiplicity){
        switch (multiplicity){
            case 1:
                return "simple";
            case 2:
                return "doble";
            case 3:
                return "triple";
            case 4:
                return "cuádruple";
            default:
                return "múltiple";
        }
    }

    public static String getMessageOfRightOption(Integer option){
        String answer;
        switch (option){
            case 1:
                if(currentRoot1 != null){
                    answer = "" + context.getText(R.string.FACTOR_COMUN_ES_EL_CORRECTO);
                    answer =  answer
                            .replace("/raiz/", "" + currentRoot1)
                            .replace("/tipo/", currentRootType);
                    if(currentRoot2 == null){
                        return ExpressionsManager.removeDecimals(answer);
                    }

                    answer += " " + context.getText(R.string.RAIZ_EXTRA);
                    return ExpressionsManager.removeDecimals(answer.replace("/raiz/", "" + currentRoot2));

                }

                return "" + context.getText(R.string.FACTOR_COMUN_NUMERICO_ES_EL_CORRECTO);

            case 2:
                if(currentRoot2 == null){
                    answer = "" + context.getText(R.string.CUADRATICA_RAIZ_DOBLE_ES_EL_CORRECTO);
                    return ExpressionsManager.removeDecimals(answer.replace("/raiz/", "" + currentRoot1));
                }
                answer = "" + context.getText(R.string.CUADRATICA_RAICES_SIMPLES_ES_EL_CORRECTO);
                return ExpressionsManager.removeDecimals(answer
                        .replace("/raiz1/", "" + currentRoot1)
                        .replace("/raiz2/", "" + currentRoot2));
            case 3:
                answer = "" + context.getText(R.string.GAUSS_ES_EL_CORRECTO);
                return ExpressionsManager.removeDecimals(answer.replace("/raiz/", "" + currentRoot1));
            default:
                return "";
        }
    }

    public static String getMessageOfRegularOptions(Integer regularOption1, Integer regularOption2){
        String regularOption1Text = regularOption1 == null ? "" : getMessageOfRegularOptionNotChosen(regularOption1);
        String regularOption2Text = regularOption2 == null ? "" : getMessageOfRegularOptionNotChosen(regularOption2);

        String answer = "";

        if(regularOption1Text.isEmpty() && regularOption2Text.isEmpty()){
            return answer;
        }

        if(!regularOption1Text.isEmpty()){
            answer += regularOption1Text;
        }

        if(!regularOption2Text.isEmpty()){
            answer += ". " + regularOption1Text;
        }

        return answer;
    }

    public static String getMessageOfRegularOptionNotChosen(Integer regularOption){
        switch (regularOption){
            case 2:
                return "" + context.getText(R.string.CUADRATICA_ERA_POSIBLE);
            case 3:
                return "" + context.getText(R.string.GAUSS_ERA_POSIBLE);
            default:
                return "";
        }
    }

    public static String getMessageOfRegularOptionChosen(Integer regularOption){
        switch (regularOption){
            case 2:
                return "" + context.getText(R.string.CUADRATICA_ES_POSIBLE_PERO_NO_LO_MEJOR);
            case 3:
                String answer = "" + context.getText(R.string.GAUSS_ES_POSIBLE_PERO_NO_LO_MEJOR);
                return ExpressionsManager.removeDecimals(answer.replace("/raiz/", "" + currentRoot1));
            default:
                return "";
        }
    }

    public static String getMessageOfRightOptionNotChosen(Integer correctOption){
        switch (correctOption){
            case 1:
                return "" + context.getText(R.string.FACTOR_COMUN_ERA_EL_CORRECTO);
            case 2:
                return "" + context.getText(R.string.CUADRATICA_ERA_EL_CORRECTO);
            case 3:
                return "" + context.getText(R.string.GAUSS_ERA_EL_CORRECTO);
            default:
                return "";
        }
    }

    public static String getMessageOfWrongOptionChosen(Integer optionChosen){
        switch (optionChosen){
            case 1:
                return "" + context.getText(R.string.FACTOR_COMUN_NO_ES_CORRECTO);
            case 2:
                return "" + context.getText(R.string.CUADRATICA_NO_ES_CORRECTO);
            case 3:
                return "" + context.getText(R.string.GAUSS_NO_ES_CORRECTO);
            default:
                return "";
        }
    }

    public static String getCaseNameFrom(Integer option){
        switch (option){
            case 1:
                return "" + context.getText(R.string.FACTOR_COMUN);
            case 2:
                return "" + context.getText(R.string.CUADRATICA);
            case 3:
                return "" + context.getText(R.string.GAUSS);
            default:
                return "";
        }
    }

    public void setUpSolveButton(Button button, RVMultipleChoiceAdapter.MultipleChoiceViewHolder holder,
                                 List<MultipleChoiceStep> multipleChoiceSteps,
                                 List<MultipleChoiceStep> currentMultipleChoiceSteps,
                                 List<RVMultipleChoiceAdapter.MultipleChoiceViewHolder> multipleChoiceViewHolders) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String summaryText;
                if(holder.chosenOption.equals(holder.correctOption)){
                    showIconForOption(holder, holder.chosenOption, R.drawable.solved_right);
                    FactoringManager.factorizeBy(holder.chosenOption);
                    String correctText = FactoringManager.getMessageOfRightOption(holder.chosenOption);
                    String complementText = FactoringManager.getMessageOfRegularOptions(holder.regularOption1, holder.regularOption2);
                    setUpMultipleChoiceExplanationsPopUp(holder, correctText + " " + complementText, null);
                    summaryText = FactoringManager.getCaseNameFrom(holder.chosenOption);
                }else if(holder.chosenOption.equals(holder.regularOption1) || holder.chosenOption.equals(holder.regularOption2)){
                    showIconForOption(holder, holder.chosenOption, R.drawable.solved_right);
                    FactoringManager.factorizeBy(holder.chosenOption);
                    String regularText = FactoringManager.getMessageOfRegularOptionChosen(holder.chosenOption);
                    String complementText = holder.chosenOption.equals(holder.regularOption1)
                            ? (holder.regularOption2 == null ? "" : FactoringManager.getMessageOfRegularOptionNotChosen(holder.regularOption2))
                            : (holder.regularOption1 == null ? "" : FactoringManager.getMessageOfRegularOptionNotChosen(holder.regularOption1));
                    String correctText = FactoringManager.getMessageOfRightOptionNotChosen(holder.correctOption);
                    setUpMultipleChoiceExplanationsPopUp(holder, regularText + " " + complementText + " " + correctText, null);
                    summaryText = FactoringManager.getCaseNameFrom(holder.chosenOption);
                }else{
                    Map<Integer, String> incorrectOptions = new HashMap<>();
                    for(int i = 1 ; i <= 3 ; i++){
                        if(i != holder.correctOption){
                            if(incorrectOptions.isEmpty()){
                                incorrectOptions.put(i, holder.incorrectOptionJustification1);
                            }else{
                                incorrectOptions.put(i, holder.incorrectOptionJustification2);
                            }
                        }
                    }
                    showIconForOption(holder, holder.correctOption, R.drawable.solved_right);
                    showIconForOption(holder, holder.chosenOption, R.drawable.solved_wrong);

                    FactoringManager.factorizeBy(holder.correctOption);
                    String regularText = (holder.regularOption1 == null ? "" : FactoringManager.getMessageOfRegularOptionNotChosen(holder.regularOption1));
                    regularText += (holder.regularOption2 == null ? "" : FactoringManager.getMessageOfRegularOptionNotChosen(holder.regularOption2));
                    String correctText = FactoringManager.getMessageOfRightOptionNotChosen(holder.correctOption);
                    setUpMultipleChoiceExplanationsPopUp(holder,correctText + " " + regularText, FactoringManager.getMessageOfWrongOptionChosen(holder.chosenOption));
                    summaryText = FactoringManager.getCaseNameFrom(holder.correctOption);
                }

                holder.summary.setText(summaryText);
                FactoringManager.setFactors();
                if(!FactoringManager.end){
                    multipleChoiceSteps.add(FactoringManager.nextStep());
                    MultipleChoiceStep currentMultipleChoiceStep = multipleChoiceSteps.get(currentMultipleChoiceSteps.size()-1);
                    currentMultipleChoiceStep.setSolved(true);
                    setUpNextStepButton(holder, currentMultipleChoiceSteps);
                }else{
                    holder.nextStep.setVisibility(View.GONE);
                    enableSummary();
                }

                setUpSolveButtonGlobal(holder, multipleChoiceSteps.get(currentMultipleChoiceSteps.size()-1), multipleChoiceSteps, currentMultipleChoiceSteps);
            }
        });
    }

    public static String getCurrentPolynomialEnteredAsText(List<Map<Integer, Double>> terms, String firstSign, Boolean enteringCoefficient){
        StringBuilder polynomial = new StringBuilder();
        polynomial.append("P(x) = ");
        for(Map<Integer, Double> term : terms){
            Integer exponent = getExponentFrom(term);
            Double coefficient = term.get(exponent);
            if(coefficient == null){
                polynomial.append(firstSign);
                polynomial.append("<b>?</b>x");
            }else if(exponent == null){
                String text;
                String sign = signOf(coefficient);
                if(enteringCoefficient){
                    text = "<b>" + sign + coefficient + "</b>x";
                }else{
                    text = sign + coefficient + "x<sup><b>?</b></sup>";
                }
                polynomial.append(text);
            }else{
                polynomial.append(signOf(coefficient));
                polynomial.append(coefficient);
                polynomial.append("x<sup>");
                int index = terms.indexOf(term);
                if(index == terms.size() - 1){
                    if(!enteringCoefficient){
                        polynomial.append("<b>" + exponent + "</b>");
                    }else{
                        polynomial.append(exponent);
                    }
                }else{
                    polynomial.append(exponent);
                }
                polynomial.append("</sup>");
            }
        }

        String expression = ExpressionsManager.removeDecimals(polynomial.toString());
        expression = expression
                .replace("= +", "= ")
                .replace("= <b>+", "= <b>")
                .replace("null", "");
        for(int i = 1 ; i <= 9 ; i++){
            expression = expression
                    .replace(i + "x<sup>0</sup>", i + "")
                    .replace(" 1x<sup>" + i, " x<sup>" + i)
                    .replace("+1x<sup>" + i, "+x<sup>" + i)
                    .replace("-1x<sup>" + i, "-x<sup>" + i);
        }
        expression = expression
                .replace("x<sup>1</sup>", "x")
                .replace("+x<sup>0</sup>", "+1")
                .replace("-x<sup>0</sup>", "-1")
                .replace("x<sup>0</sup>", "1");

        return expression;
    }

    public static Map<Integer, Double> getCurrentPolynomialEnteredSortedAndSimplified(List<Map<Integer, Double>> polynomialTermsEntered){
        Map<Integer, Double> polynomial = new HashMap<>();
        for(Map<Integer, Double> term : polynomialTermsEntered){
            Integer exponent = getExponentFrom(term);
            if(polynomial.containsKey(exponent)){
                Double newCoefficient = polynomial.get(exponent) + term.get(exponent);
                polynomial.put(exponent, newCoefficient);
            }else{
                polynomial.put(exponent, term.get(exponent));
            }
        }
        return polynomial;
    }

    public static String getCurrentPolynomialEnteredSortedAndSimplifiedAsText(Map<Integer, Double> polynomial){
        List<Map<Integer, Double>> polynomialSorted = new ArrayList<>();
        List<Integer> exponents = new ArrayList<>(polynomial.keySet());
        Collections.sort(exponents, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 >= o1 ? 1 : -1;
            }
        });

        for(Integer exponent : exponents){
            polynomialSorted.add(new HashMap<Integer, Double>(){{
                put(exponent, polynomial.get(exponent));
            }});
        }

        String result = getCurrentPolynomialEnteredAsText(polynomialSorted, null, false);
        return result
                .replace("<b>", "")
                .replace("</b>", "");
    }

    private static String signOf(Double number){
        return number >= 0 ? "+" : "";
    }

    public static Integer getExponentFrom(Map<Integer, Double> term){
        return (new ArrayList<>(term.keySet())).get(0);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return SolvePolynomialActivity.recyclerView;
    }
}
