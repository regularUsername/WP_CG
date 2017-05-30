package computergraphics.datastructures.bsp;

import computergraphics.math.PrincipalComponentAnalysis;
import computergraphics.math.Vector;

import java.util.ArrayList;
import java.util.List;

public class BspTreeToolsDummy {
    /**
     * Recursively create a BSP tree for a given set of points.
     *
     * @param parentNode   Parent scene graph node
     * @param allPoints    List with all point positions in the dataset
     * @param pointIndices if indices used in the current recursive call
     */
    public BspTreeNode createBspTree(BspTreeNode parentNode, List<Vector> allPoints, List<Integer> pointIndices) {
        BspTreeNode bspTreeNode = new BspTreeNode();

        if (pointIndices.size() > 2) {
            PrincipalComponentAnalysis pca = new PrincipalComponentAnalysis();
            for (Integer i : pointIndices) {
                pca.add(allPoints.get(i));
            }
            pca.applyPCA();


            Vector ev = pca.getEigenValues();
            int maxI = 0;
            double max = ev.get(0);
            if (ev.get(1) > max) {
                max = ev.get(1);
                maxI = 1;
            }
            if (ev.get(2) > max) {
                maxI = 2;
            }
            bspTreeNode.setP(pca.getCentroid());
            bspTreeNode.setN(pca.getEigenVector(maxI));

        } else if (pointIndices.size() == 2) {
            Vector a = allPoints.get(pointIndices.get(0));
            Vector b = allPoints.get(pointIndices.get(1));
            bspTreeNode.setP(a.add(b).multiply(1.0 / 2.0));
            bspTreeNode.setN(b.subtract(a).getNormalized());
        }

        if (pointIndices.size() > 1) {
            List<Integer> pos = new ArrayList<>();
            List<Integer> neg = new ArrayList<>();
            for (Integer i : pointIndices) {
                Vector p = allPoints.get(i);
                if (bspTreeNode.IsPositive(p)) {
                    bspTreeNode.AddElement(BspTreeNode.Orientation.POSITIVE, i);
                    pos.add(i);
                } else {
                    bspTreeNode.AddElement(BspTreeNode.Orientation.NEGATIVE, i);
                    neg.add(i);
                }
            }


            if (pos.size() > 0) {
                bspTreeNode.SetChild(BspTreeNode.Orientation.POSITIVE, createBspTree(bspTreeNode, allPoints, pos));
            }
            if (neg.size() > 0) {
                bspTreeNode.SetChild(BspTreeNode.Orientation.NEGATIVE, createBspTree(bspTreeNode, allPoints, neg));
            }
        } else {
            bspTreeNode.setN(new Vector(0,0,0));
            bspTreeNode.setP(allPoints.get(pointIndices.get(0)));
            bspTreeNode.AddElement(BspTreeNode.Orientation.POSITIVE, pointIndices.get(0));
        }


        return bspTreeNode;

    }

    /**
     * Compute the back-to-front ordering for all points in 'points' based on the
     * tree in 'node' and the given eye position
     *
     * @param node   Root node of the BSP tree
     * @param points List of points to be considered
     * @param eye    Observer position
     * @return Sorted (back-to-front) list of points
     */
    public List<Integer> getBackToFront(BspTreeNode node, List<Vector> points, Vector eye) {
        List<Integer> res = new ArrayList<>();
        if(node.getNumberOfElements(BspTreeNode.Orientation.POSITIVE) == 1 &&
                node.getNumberOfElements(BspTreeNode.Orientation.NEGATIVE) == 0){
            res.add(node.getElement(BspTreeNode.Orientation.POSITIVE,0));
            return res;
        }else if(node.getNumberOfElements(BspTreeNode.Orientation.POSITIVE) == 0 &&
                node.getNumberOfElements(BspTreeNode.Orientation.NEGATIVE) == 1){
            res.add(node.getElement(BspTreeNode.Orientation.NEGATIVE,0));
            return res;
        }


        if (node.IsPositive(eye)) {
            BspTreeNode child = node.GetChild(BspTreeNode.Orientation.NEGATIVE);
            if (child != null) {
                res.addAll(getBackToFront(child, points, eye));
            }
            child = node.GetChild(BspTreeNode.Orientation.POSITIVE);
            if (child != null) {
                res.addAll(getBackToFront(child, points, eye));
            }
        } else {
            BspTreeNode child = node.GetChild(BspTreeNode.Orientation.POSITIVE);
            if (child != null) {
                res.addAll(getBackToFront(child, points, eye));
            }
            child = node.GetChild(BspTreeNode.Orientation.NEGATIVE);
            if (child != null) {
                res.addAll(getBackToFront(child, points, eye));
            }

        }

        return res;
    }
}
