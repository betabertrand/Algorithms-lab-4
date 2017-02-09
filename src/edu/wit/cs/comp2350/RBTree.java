package edu.wit.cs.comp2350;

public class RBTree extends LocationHolder {

    private DiskLocation nil = new DiskLocation(-1, -1);
    private DiskLocation root;

    private void setRed(DiskLocation z) {
        if (z != nil)
            z.color = RB.RED;
    }

    @Override
    public DiskLocation find(DiskLocation d) {
        // TODO Auto-generated method stub
        return _find(d, root);
    }

    private DiskLocation _find(DiskLocation d, DiskLocation curr) {
        if (curr == nil)
            return null;
        else if (d.equals(curr))
            return curr;
        else if (d.isGreaterThan(curr))
            return _find(d, curr.right);
        else
            return _find(d, curr.left);
    }

    @Override
    public DiskLocation next(DiskLocation d) {
        // TODO Auto-generated method stub
        if (d.right != nil)
            return min(d.right);
        else
            return upNext(d);

    }

    @Override
    public DiskLocation prev(DiskLocation d) {
        // TODO Auto-generated method stub

        if (d.right != nil)
            return max(d.left);
        else
            return upPrev(d);
    }

    @Override
    public void insert(DiskLocation d) {
        // TODO Auto-generated method stub


        d.left = nil;
        d.right = nil;

        if (root == null) {
            root = d;
            d.parent = nil;
            nil.left = nil;
            nil.right = nil;
            nil.parent = nil;
        }

        d.parent = findParent(d, root, nil);
        if (d.parent == nil) {
            root = d;
        } else {
            if (d.parent.isGreaterThan(d)) {
                d.parent.left = d;
            } else {
                d.parent.right = d;
            }
        }
        d.left = nil;
        d.right = nil;
        d.color = RB.RED;
        fixUp(d);

        /* tried the books sudo code and it didnt work so i used the sudo code from the powerpoint
        DiskLocation y = nil;
        DiskLocation x = root;

        while (x == nil) { // making == fixes most test
            y = x;
            if (x.isGreaterThan(d)) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        d.parent = y;
        if (y.equals(nil)) {
            root = d;
        } else if (y.isGreaterThan(d)) {
            y.left = d;
        } else {
            y.right = d;
        }

        d.left = nil;
        d.right = nil;

        setRed(d);
        fixUp(d);

        */

    }
    private DiskLocation findParent(DiskLocation d, DiskLocation curr, DiskLocation parent) {
        if (curr == nil)
            return parent;
        if (d.isGreaterThan(curr))
            return findParent(d, curr.right, curr);
        else
            return findParent(d, curr.left, curr);
    }

    @Override
    public int height() {
        // TODO Auto-generated method stub
        return _height(root);
    }

    public void rotateLeft(DiskLocation x) {
        DiskLocation y = x.right;
        x.right = y.left;
        if(y.left != nil)
        {
            y.left.parent = x;

        }
        y.parent = x.parent;
        if(x.parent == nil){
            root = y;
        }
        else if(x == x.parent.left){
            x.parent.left = y;
        }

        else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;


    }

    public void rotateRight(DiskLocation x) {


        DiskLocation y = x.left;
        x.left =y.right;

        if(y.right != nil){
            y.right.parent = x;
        }
        y.parent = x.parent;
        if(x.parent == nil){
            root = y;
        }
        else if (x == x.parent.right)
        {
            x.parent.right = y;
        }
        else
        {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public void fixUp(DiskLocation z) {
        while (z.parent.color == RB.RED) {
            if (z.parent == z.parent.parent.left) {
                DiskLocation y = z.parent.parent.right;
                if (y.color == RB.RED) {
                    z.parent.color = RB.BLACK;
                    y.color = RB.BLACK;
                    z.parent.parent.color = RB.RED;
                    z = z.parent.parent;
                } else if (z == z.parent.right) {
                    z = z.parent;
                    rotateLeft(z);
                } else {
                    z.parent.color = RB.BLACK;
                    z.parent.parent.color = RB.RED;
                    rotateRight(z.parent.parent);

                }
            } else {
                DiskLocation y = z.parent.parent.left;
                if (y.color == RB.RED) {
                    z.parent.color = RB.BLACK;
                    y.color = RB.BLACK;
                    z.parent.parent.color = RB.RED;
                    z = z.parent.parent;
                } else if (z == z.parent.left) {
                    z = z.parent;
                    rotateRight(z);
                } else {
                    z.parent.color = RB.BLACK;
                    z.parent.parent.color = RB.RED;
                    rotateLeft(z.parent.parent);
                }

            }

        }
        root.color = RB.BLACK;

    }




    private DiskLocation upNext(DiskLocation d) {
        DiskLocation p = d.parent;
        if (p == nil || d == p.left)
            return p;
        else
            return upNext(p);
    }

    private DiskLocation upPrev(DiskLocation d) {
        DiskLocation p = d.parent;
        if (p == nil || d == p.right)
            return p;
        else
            return upPrev(p);
    }
    private DiskLocation min(DiskLocation d) {
        if (d.left == nil)
            return d;
        else
            return min(d.left);
    }
    private DiskLocation max(DiskLocation d) {
        if (d.right == nil)
            return d;
        else
            return max(d.right);
    }

    private int _height(DiskLocation d) {
        if (d == nil)
            return 0;
        return 1 + Math.max(_height(d.right), _height(d.left));
    }
}





// _height uprev upnext findparent