package ar.com.profebot.resolutor;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ar.com.profebot.parser.container.Tree;
import ar.com.profebot.parser.container.TreeNode;
import ar.com.profebot.parser.exception.InvalidExpressionException;
import ar.com.profebot.parser.service.ParserService;
import ar.com.profebot.resolutor.container.NodeStatus;
import ar.com.profebot.resolutor.service.ResolutorService;
import ar.com.profebot.resolutor.utils.TreeUtils;

public class ResolutorServiceTest extends ResolutorService {

    @Test
    public void getFactorPairs_ok1() {
        List<Integer[]> factors = super.getFactorPairs(1,true);
        List<Integer[]> expected = new ArrayList<>();
        expected.add(new Integer[]{-1,-1});
        expected.add(new Integer[] {1,1});

        Assert.assertEquals(expected.size(), factors.size());
        for (int i=0; i <factors.size(); i++){
            Assert.assertArrayEquals(factors.get(i), expected.get(i));
        }
    }

    @Test
    public void getFactorPairs_ok2() {
        List<Integer[]> factors = super.getFactorPairs(5,true);
        List<Integer[]> expected = new ArrayList<Integer[]>();
        expected.add(new Integer[]{-1,-5});
        expected.add(new Integer[] {1,5});

        Assert.assertEquals(expected.size(), factors.size());
        for (int i=0; i <factors.size(); i++){
            Assert.assertArrayEquals(factors.get(i), expected.get(i));
        }

    }

    @Test
    public void getFactorPairs_ok3() {
        List<Integer[]> factors = super.getFactorPairs(12,true);
        List<Integer[]> expected = new ArrayList<Integer[]>();
        expected.add(new Integer[]{-3,-4});
        expected.add(new Integer[] {-2,-6});
        expected.add(new Integer[]{-1,-12});
        expected.add(new Integer[] {1,12});
        expected.add(new Integer[] {2,6});
        expected.add(new Integer[] {3,4});

        Assert.assertEquals(expected.size(), factors.size());
        for (int i=0; i <factors.size(); i++){
            Assert.assertArrayEquals(factors.get(i), expected.get(i));
        }
    }

    @Test
    public void getFactorPairs_ok4() {
        List<Integer[]> factors = super.getFactorPairs(-12,true);
        List<Integer[]> expected = new ArrayList<Integer[]>();
        expected.add(new Integer[]{-3,4});
        expected.add(new Integer[] {-2,6});
        expected.add(new Integer[]{-1,12});
        expected.add(new Integer[] {1,-12});
        expected.add(new Integer[] {2,-6});
        expected.add(new Integer[] {3,-4});

        Assert.assertEquals(expected.size(), factors.size());
        for (int i=0; i <factors.size(); i++){
            Assert.assertArrayEquals(factors.get(i), expected.get(i));
        }
    }

    @Test
    public void getFactorPairs_ok5() {
        List<Integer[]> factors = super.getFactorPairs(15,true);
        List<Integer[]> expected = new ArrayList<Integer[]>();
        expected.add(new Integer[]{-3,-5});
        expected.add(new Integer[] {-1,-15});
        expected.add(new Integer[]{1,15});
        expected.add(new Integer[] {3,5});

        Assert.assertEquals(expected.size(), factors.size());
        for (int i=0; i <factors.size(); i++){
            Assert.assertArrayEquals(factors.get(i), expected.get(i));
        }
    }

    @Test
    public void getFactorPairs_ok6() {
        List<Integer[]> factors = super.getFactorPairs(36,true);
        List<Integer[]> expected = new ArrayList<Integer[]>();
        expected.add(new Integer[]{-6,-6});
        expected.add(new Integer[] {-4,-9});
        expected.add(new Integer[]{-3,-12});
        expected.add(new Integer[] {-2,-18});
        expected.add(new Integer[] {-1,-36});
        expected.add(new Integer[] {1,36});
        expected.add(new Integer[] {2,18});
        expected.add(new Integer[]{3,12});
        expected.add(new Integer[] {4,9});
        expected.add(new Integer[]{6,6});

        Assert.assertEquals(expected.size(), factors.size());
        for (int i=0; i <factors.size(); i++){
            Assert.assertArrayEquals(factors.get(i), expected.get(i));
        }
    }

    @Test
    public void getFactorPairs_ok7() {
        List<Integer[]> factors = super.getFactorPairs(49,true);
        List<Integer[]> expected = new ArrayList<Integer[]>();
        expected.add(new Integer[]{-7,-7});
        expected.add(new Integer[] {-1,-49});
        expected.add(new Integer[]{1,49});
        expected.add(new Integer[] {7,7});

        Assert.assertEquals(expected.size(), factors.size());
        for (int i=0; i <factors.size(); i++){
            Assert.assertArrayEquals(factors.get(i), expected.get(i));
        }
    }

    @Test
    public void getFactorPairs_ok8() {
        List<Integer[]> factors = super.getFactorPairs(1260,true);
        List<Integer[]> expected = new ArrayList<Integer[]>();
        expected.add(new Integer[]{-35,-36});
        expected.add(new Integer[] {-30,-42});
        expected.add(new Integer[]{-28,-45});
        expected.add(new Integer[] {-21,-60});
        expected.add(new Integer[]{-20,-63});
        expected.add(new Integer[] {-18,-70});
        expected.add(new Integer[]{-15,-84});
        expected.add(new Integer[] {-14,-90});
        expected.add(new Integer[]{-12,-105});
        expected.add(new Integer[] {-10,-126});
        expected.add(new Integer[]{-9,-140});
        expected.add(new Integer[] {-7,-180});
        expected.add(new Integer[]{-6,-210});
        expected.add(new Integer[] {-5,-252});
        expected.add(new Integer[] {-4,-315});
        expected.add(new Integer[]{-3,-420});
        expected.add(new Integer[] {-2,-630});
        expected.add(new Integer[] {-1,-1260});
        expected.add(new Integer[]{1,1260});
        expected.add(new Integer[] {2,630});
        expected.add(new Integer[]{3,420});
        expected.add(new Integer[] {4,315});
        expected.add(new Integer[]{5,252});
        expected.add(new Integer[] {6,210});
        expected.add(new Integer[]{7,180});
        expected.add(new Integer[] {9,140});
        expected.add(new Integer[]{10,126});
        expected.add(new Integer[] {12,105});
        expected.add(new Integer[]{14,90});
        expected.add(new Integer[] {15,84});
        expected.add(new Integer[]{18,70});
        expected.add(new Integer[] {20,63});
        expected.add(new Integer[]{21,60});
        expected.add(new Integer[] {28,45});
        expected.add(new Integer[] {30,42});
        expected.add(new Integer[] {35,36});


        Assert.assertEquals(expected.size(), factors.size());
        for (int i=0; i <factors.size(); i++){
            Assert.assertArrayEquals(expected.get(i), factors.get(i));
        }
    }

    @Test
    public void getFactorPairs_ok9() {
        List<Integer[]> factors = super.getFactorPairs(1234567891,true);
        List<Integer[]> expected = new ArrayList<Integer[]>();
        expected.add(new Integer[]{-1,-1234567891});
        expected.add(new Integer[] {1,1234567891});

        Assert.assertEquals(expected.size(), factors.size());
        for (int i=0; i <factors.size(); i++){
            Assert.assertArrayEquals(factors.get(i), expected.get(i));
        }
    }

    @Test
    public void factorStepThrough_ok1() throws InvalidExpressionException {
        String expression = " X^2 = 9";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        List<NodeStatus> estados = super.factorStepThrough(node,false);
        String lastStep;
        if (estados.size() == 0) {
            lastStep = "NO_STEPS";
        }
        else {
            lastStep = estados.get(estados.size() - 1).getNewNode().toExpression();
        }
        Assert.assertEquals("NO_STEPS", lastStep);
    }

    @Test
    public void factorStepThrough_ok2() throws InvalidExpressionException {
        String expression = " X^2 + 2X = 9";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        List<NodeStatus> estados = super.factorStepThrough(node,false);
        String lastStep;
        if (estados.size() == 0) {
            lastStep = "NO_STEPS";
        }
        else {
            lastStep = estados.get(estados.size() - 1).getNewNode().toExpression();
        }
        Assert.assertEquals("X*(X+2)", lastStep);
    }

    @Test
    public void factorStepThrough_ok3() throws InvalidExpressionException {
        String expression = " X^2 - 4 = 9";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        List<NodeStatus> estados = super.factorStepThrough(node,false);
        String lastStep;
        if (estados.size() == 0) {
            lastStep = "NO_STEPS";
        }
        else {
            lastStep = estados.get(estados.size() - 1).getNewNode().toExpression();
        }
        Assert.assertEquals("(X+2)*(X-2)", lastStep);
    }

    @Test
    public void factorStepThrough_ok4() throws InvalidExpressionException {
        String expression = " X^2 + 4 = 9";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        List<NodeStatus> estados = super.factorStepThrough(node,false);
        String lastStep;
        if (estados.size() == 0) {
            lastStep = "NO_STEPS";
        }
        else {
            lastStep = estados.get(estados.size() - 1).getNewNode().toExpression();
        }
        Assert.assertEquals("NO_STEPS", lastStep);
    }

    @Test
    public void factorStepThrough_ok5() throws InvalidExpressionException {
        String expression = " X^2 + 2X + 1 = 9";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        List<NodeStatus> estados = super.factorStepThrough(node,false);
        String lastStep;
        if (estados.size() == 0) {
            lastStep = "NO_STEPS";
        }
        else {
            lastStep = estados.get(estados.size() - 1).getNewNode().toExpression();
        }
        Assert.assertEquals("(X+1)^2", lastStep);
    }

    @Test
    public void factorStepThrough_ok6() throws InvalidExpressionException {
        String expression = " X^2 + 3X + 2 = 9";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        List<NodeStatus> estados = super.factorStepThrough(node,false);
        String lastStep;
        if (estados.size() == 0) {
            lastStep = "NO_STEPS";
        }
        else {
            lastStep = estados.get(estados.size() - 1).getNewNode().toExpression();
        }
        Assert.assertEquals("(X+1)*(X+2)", lastStep);
    }

    @Test
    public void factorStepThrough_ok7() throws InvalidExpressionException {
        String expression = " X^3 + X^2 + X + 1 = 9";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        List<NodeStatus> estados = super.factorStepThrough(node,false);
        String lastStep;
        if (estados.size() == 0) {
            lastStep = "NO_STEPS";
        }
        else {
            lastStep = estados.get(estados.size() - 1).getNewNode().toExpression();
        }
        Assert.assertEquals("NO_STEPS", lastStep);
    }

    @Test
    public void factorStepThrough_ok8() throws InvalidExpressionException {
        String expression = " 1 + 2 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        List<NodeStatus> estados = super.factorStepThrough(node,false);
        String lastStep;
        if (estados.size() == 0) {
            lastStep = "NO_STEPS";
        }
        else {
            lastStep = estados.get(estados.size() - 1).getNewNode().toExpression();
        }
        Assert.assertEquals("NO_STEPS", lastStep);
    }

    @Test
    public void factorStepThrough_ok9() throws InvalidExpressionException {
        String expression = " X + 2 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        List<NodeStatus> estados = super.factorStepThrough(node,false);
        String lastStep;
        if (estados.size() == 0) {
            lastStep = "NO_STEPS";
        }
        else {
            lastStep = estados.get(estados.size() - 1).getNewNode().toExpression();
        }
        Assert.assertEquals("NO_STEPS", lastStep);
    }

    @Test
    public void factorQuadratic_ok1() throws InvalidExpressionException {
        String expression = " X^2 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("X^2", newExpression);
    }

    @Test
    public void factorQuadratic_ok2() throws InvalidExpressionException {
        String expression = " X^2 + X^2 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("X^2+X^2", newExpression);
    }

    @Test
    public void factorQuadratic_ok3() throws InvalidExpressionException {
        String expression = " X^2 + 2 - 3 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("X^2+2-3", newExpression);
    }

    @Test
    public void factorQuadratic_ok4() throws InvalidExpressionException {
        String expression = " X^2 + 4 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("X^2+4", newExpression);
    }

    @Test
    public void factorQuadratic_ok5() throws InvalidExpressionException {
        String expression = " -X^2 - 1 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("-X^2-1", newExpression);
    }

    @Test
    public void factorQuadratic_ok6() throws InvalidExpressionException {
        String expression = " X^2 + 2X = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("X*(X+2)", newExpression);
    }

    @Test
    public void factorQuadratic_ok7() throws InvalidExpressionException {
        String expression = " -X^2 - 2X = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("-X*(X+2)", newExpression);
    }

    @Test
    public void factorQuadratic_ok8() throws InvalidExpressionException {
        String expression = " X^2 - 3X = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("X*(X-3)", newExpression);
    }

    @Test
    public void factorQuadratic_ok9() throws InvalidExpressionException {
        String expression = " 2X^2 + 4X= 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("2X*(X+2)", newExpression);
    }

    @Test
    public void factorQuadratic_ok10() throws InvalidExpressionException {
        String expression = " X^2 - 4= 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("(X-2)*(X+2)", newExpression);
    }

    @Test
    public void factorQuadratic_ok11() throws InvalidExpressionException {
        String expression = " -X^2 + 1 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("-(X+1)*(X-1)", newExpression);
    }

    @Test
    public void factorQuadratic_ok12() throws InvalidExpressionException {
        String expression = " 4X^2 - 9 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("(2X-3)*(2X+3)", newExpression);
    }

    @Test
    public void factorQuadratic_ok13() throws InvalidExpressionException {
        String expression = " 4X^2 - 16 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("4*(X-2)*(X+2)", newExpression);
    }

    @Test
    public void factorQuadratic_ok14() throws InvalidExpressionException {
        String expression = " -4X^2 + 16 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("-4*(X-2)*(X+2)", newExpression);
    }

    @Test
    public void factorQuadratic_ok15() throws InvalidExpressionException {
        String expression = " X^2 + 2X + 1 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("(X+1)^2", newExpression);
    }

    @Test
    public void factorQuadratic_ok16() throws InvalidExpressionException {
        String expression = " X^2 - 2X + 1 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("(X-1)^2", newExpression);
    }

    @Test
    public void factorQuadratic_ok17() throws InvalidExpressionException {
        String expression = " -X^2 - 2X - 1 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("-(X+1)^2", newExpression);
    }

    @Test
    public void factorQuadratic_ok18() throws InvalidExpressionException {
        String expression = " 4X^2 + 4X + 1 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("(2X+1)^2", newExpression);
    }

    @Test
    public void factorQuadratic_ok19() throws InvalidExpressionException {
        String expression = " 12X^2 + 12X + 3 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("3*(2X+1)^2", newExpression);
    }

    @Test
    public void factorQuadratic_ok20() throws InvalidExpressionException {
        String expression = " X^2 + 3X + 2 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("(X+1)*(X+2)", newExpression);
    }

    @Test
    public void factorQuadratic_ok21() throws InvalidExpressionException {
        String expression = " X^2 - 3X + 2 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("(X-1)*(X-2)", newExpression);
    }

    @Test
    public void factorQuadratic_ok22() throws InvalidExpressionException {
        String expression = " X^2 + X - 2 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("(X-1)*(X+2)", newExpression);
    }

    @Test
    public void factorQuadratic_ok23() throws InvalidExpressionException {
        String expression = " -X^2 - 3X - 2 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("-(X+1)*(X+2)", newExpression);
    }

    @Test
    public void factorQuadratic_ok24() throws InvalidExpressionException {
        String expression = " 2X^2 + 5X + 3 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("(X+1)*(2X+3)", newExpression);
    }

    @Test
    public void factorQuadratic_ok25() throws InvalidExpressionException {
        String expression = " 2X^2 - 5X - 3 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("(2X+1)*(X-3)", newExpression);
    }

    @Test
    public void factorQuadratic_ok26() throws InvalidExpressionException {
        String expression = " 2X^2 - 5X + 3 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("(X-1)*(2X-3)", newExpression);
    }

    @Test
    public void factorQuadratic_ok27() throws InvalidExpressionException {
        String expression = " X^2 + 4X + 1 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("X^2+4X+1", newExpression);
    }

    @Test
    public void factorQuadratic_ok28() throws InvalidExpressionException {
        String expression = " X^2 - 3X + 1 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        String newExpression = estado.getNewNode().toExpression();
        Assert.assertEquals("X^2-3X+1", newExpression);
    }

    @Test
    public void factorQuadratic_ok29() throws InvalidExpressionException {
        String expression = " X^2 - 3X + 1 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        List<NodeStatus> subpasos = estado.getSubsteps();
        ArrayList<String> expectedResults = new ArrayList<String>();
        expectedResults.add("X^2+X+2X+2");
        expectedResults.add("(X^2+X)+(2X+2)");
        expectedResults.add("X*(X+1)+(2X+2)");
        expectedResults.add("X*(X+1)+2*(X+1)");
        expectedResults.add("(X+1)*(X+2)");
        for (int i=0; i <subpasos.size(); i++){
            Assert.assertEquals(expectedResults.get(i), subpasos.get(i).getNewNode().toExpression());
        };
    }

    @Test
    public void factorQuadratic_ok30() throws InvalidExpressionException {
        String expression = " X^2 - 3X + 2 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        List<NodeStatus> subpasos = estado.getSubsteps();
        ArrayList<String> expectedResults = new ArrayList<String>();
        expectedResults.add("X^2-X-2X+2");
        expectedResults.add("(X^2-X)+(-2X+2)");
        expectedResults.add("X*(X-1)+(-2X+2)");
        expectedResults.add("X*(X-1)-2*(X-1)");
        expectedResults.add("(X-1)*(X-2)");
        for (int i=0; i <subpasos.size(); i++){
            Assert.assertEquals(expectedResults.get(i), subpasos.get(i).getNewNode().toExpression());
        };
    }

    @Test
    public void factorQuadratic_ok31() throws InvalidExpressionException {
        String expression = " X^2 + X - 2 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        List<NodeStatus> subpasos = estado.getSubsteps();
        ArrayList<String> expectedResults = new ArrayList<String>();
        expectedResults.add("X^2-X+2X-2");
        expectedResults.add("(X^2-X)+(2X-2)");
        expectedResults.add("X*(X-1)+(2X-2)");
        expectedResults.add("X*(X-1)+2*(X-1)");
        expectedResults.add("(X-1)*(X+2)");
        for (int i=0; i <subpasos.size(); i++){
            Assert.assertEquals(expectedResults.get(i), subpasos.get(i).getNewNode().toExpression());
        };
    }

    @Test
    public void factorQuadratic_ok32() throws InvalidExpressionException {
        String expression = " -X^2 - 3X - 2 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        List<NodeStatus> subpasos = estado.getSubsteps();
        ArrayList<String> expectedResults = new ArrayList<String>();
        expectedResults.add("-(X^2+3X+2)");
        expectedResults.add("-(X^2+X+2X+2)");
        expectedResults.add("-((X^2+X)+(2X+2))");
        expectedResults.add("-(X*(X+1)+(2X+2))");
        expectedResults.add("-(X*(X+1)+2*(X+1))");
        expectedResults.add("-(X+1)*(X+2)");
        for (int i=0; i <subpasos.size(); i++){
            Assert.assertEquals(expectedResults.get(i), subpasos.get(i).getNewNode().toExpression());
        };
    }

    @Test
    public void factorQuadratic_ok33() throws InvalidExpressionException {
        String expression = " 2X^2 + 5X + 3 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        List<NodeStatus> subpasos = estado.getSubsteps();
        ArrayList<String> expectedResults = new ArrayList<String>();
        expectedResults.add("2X^2+2X+3X+3");
        expectedResults.add("(2X^2+2X)+(3X+3)");
        expectedResults.add("2X*(X+1)+(3X+3)");
        expectedResults.add("2X*(X+1)+3*(X+1)");
        expectedResults.add("(X+1)*(2X+3)");
        for (int i=0; i <subpasos.size(); i++){
            Assert.assertEquals(expectedResults.get(i), subpasos.get(i).getNewNode().toExpression());
        };
    }

    @Test
    public void factorQuadratic_ok34() throws InvalidExpressionException {
        String expression = " 2X^2 - 5X - 3 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        List<NodeStatus> subpasos = estado.getSubsteps();
        ArrayList<String> expectedResults = new ArrayList<String>();
        expectedResults.add("2X^2+X-6X-3");
        expectedResults.add("(2X^2+X)+(-6X-3)");
        expectedResults.add("X*(2X+1)+(-6X-3)");
        expectedResults.add("X*(2X+1)-3*(2X+1)");
        expectedResults.add("(2X+1)*(X-3)");
        for (int i=0; i <subpasos.size(); i++){
            Assert.assertEquals(expectedResults.get(i), subpasos.get(i).getNewNode().toExpression());
        };
    }

    @Test
    public void factorQuadratic_ok35() throws InvalidExpressionException {
        String expression = " 2X^2 - 5X + 3 = 3";
        Tree tree = (new ParserService()).parseExpression(expression);
        TreeNode node = tree.getRootNode().getLeftNode();
        TreeNode flattenedNode = TreeUtils.flattenOperands(node);
        NodeStatus estado = super.factorQuadratic(flattenedNode);
        List<NodeStatus> subpasos = estado.getSubsteps();
        ArrayList<String> expectedResults = new ArrayList<String>();
        expectedResults.add("2X^2-2X-3X+3");
        expectedResults.add("(2X^2-2X)+(-3X+3)");
        expectedResults.add("2X*(X-1)+(-3X+3)");
        expectedResults.add("2X*(X-1)-3*(X-1)");
        expectedResults.add("(X-1)*(2X-3)");
        for (int i=0; i <subpasos.size(); i++){
            Assert.assertEquals(expectedResults.get(i), subpasos.get(i).getNewNode().toExpression());
        };
    }
}
