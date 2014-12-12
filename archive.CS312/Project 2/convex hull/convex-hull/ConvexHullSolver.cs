using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;
using System.Collections;
using Convex_Hull;
using Hull_Node;

namespace _2_convex_hull
{
    class ConvexHullSolver
    {
        System.Drawing.Graphics g;
        System.Windows.Forms.PictureBox pictureBoxView;

        public ConvexHullSolver(System.Drawing.Graphics g, System.Windows.Forms.PictureBox pictureBoxView)
        {
            this.g = g;
            this.pictureBoxView = pictureBoxView;
        }

        public void Refresh()
        {
            // Use this especially for debugging and whenever you want to see what you have drawn so far
            pictureBoxView.Refresh();
        }

        public void Pause(int milliseconds)
        {
            // Use this especially for debugging and to animate your algorithm slowly
            pictureBoxView.Refresh();
            System.Threading.Thread.Sleep(milliseconds);
        }

        public void Solve(List<PointF> pointList)
        {
            //Sort List of Points
            pointList.Sort((a, b) =>
            {
                int result = a.X.CompareTo(b.X);
                if (result == 0) result = a.Y.CompareTo(b.Y);
                return result;
            });


            //Divide and Conquer the List
            ConvexHull hull = DivideConquer(pointList);
            DrawHull(hull, Color.Green);

        }

        public void DrawHull(ConvexHull hull, Color color)
        {
            // Create pen.
            Pen blackPen = new Pen(color, 2);

            HullNode current = hull.leftMost;

            g.DrawLine(blackPen, current.point, current.next.point);
            current = current.next;

            // Draw line to screen.
            while (hull.leftMost.point.X != current.point.X)
            {
                g.DrawLine(blackPen, current.point, current.next.point);
                current = current.next;
            }

        }

        public ConvexHull DivideConquer(List<PointF> pointList)
        {

            ConvexHull leftHull;
            ConvexHull rightHull;
            if (pointList.Count > 3)
            {
                Decimal half = pointList.Count / 2;
                int intHalf = (int)Math.Floor(half);
                List<PointF> left = pointList.GetRange(0, intHalf);
                List<PointF> right = pointList.GetRange(intHalf, pointList.Count - intHalf);
                leftHull = DivideConquer(left);
                rightHull = DivideConquer(right);
            }
            else
            {
                //Base case of size 3
                if (pointList.Count == 3)
                {
                    return BaseCreateHull(pointList);
                }
                else //Base case of size 2
                {
                    HullNode leftMost;
                    HullNode middle;
                    HullNode rightMost;
                    leftMost = new HullNode(pointList[0]);
                    rightMost = new HullNode(pointList[1]);
                    leftMost.next = rightMost;
                    leftMost.previous = rightMost;
                    rightMost.next = leftMost;
                    rightMost.previous = leftMost;
                    

                    ConvexHull hull = new ConvexHull(leftMost, rightMost);
                    //DrawHull(hull, Color.Red);
                    return hull;
                }
            }

            return Merge(leftHull, rightHull);

        }

        public ConvexHull BaseCreateHull(List<PointF> pointList)
        {
            float slope_one = GetSlope(pointList[0], pointList[1]);
            float slope_two = GetSlope(pointList[0], pointList[2]);
            HullNode leftMost = new HullNode(pointList[0]);
            HullNode middle = new HullNode(pointList[1]);
            HullNode rightMost = new HullNode(pointList[2]);

            if (slope_one > slope_two)
            {
                leftMost.next = middle;
                leftMost.previous = rightMost;
                
                middle.next = rightMost;
                middle.previous = leftMost;
                
                rightMost.next = leftMost;
                rightMost.previous = middle;
            }
            else
            {
                leftMost.next = rightMost;
                leftMost.previous = middle;
                
                middle.next = leftMost;
                middle.previous = rightMost;
                
                rightMost.next = middle;
                rightMost.previous = leftMost;
            }
            ConvexHull hull = new ConvexHull(leftMost, rightMost);


            //DrawHull(hull, Color.Blue);
            return hull;
        }

        public ConvexHull Merge(ConvexHull left, ConvexHull right)
        {
            //TODO Implement this function
            ConvexHull mergedHull = new ConvexHull(left.leftMost, right.rightMost);
            HullNode leftUpper = left.rightMost;
            HullNode rightUpper = right.leftMost;
            HullNode leftLower = left.rightMost;
            HullNode rightLower = right.leftMost;
            


            bool runAgain = true;
            //Find Upper Tangent Line
            while (runAgain)
            {
                runAgain = false;
                bool aMoved = true;
                while (aMoved)
                {
                    aMoved = false;
                    double slopeOne = GetSlope(leftUpper.point, rightUpper.point);
                    double SlopeTwo = GetSlope(leftUpper.previous.point, rightUpper.point);
                    if (slopeOne > SlopeTwo )
                    {
                        leftUpper = leftUpper.previous;
                        aMoved = true;
                    }
                }
                bool bMoved = true;
                while (bMoved)
                {
                    bMoved = false;
                    double slopeOne = GetSlope(leftUpper.point, rightUpper.point);
                    double SlopeTwo = GetSlope(leftUpper.point, rightUpper.next.point);

                    if (slopeOne < SlopeTwo)
                    {
                        rightUpper = rightUpper.next;
                        bMoved = true;
                        runAgain = true;
                    }
                }
            }

            //Find Lower Tangent Line
            runAgain = true;
            while (runAgain)
            {
                runAgain = false;
                bool aMoved = true;
                while (aMoved)
                {
                    aMoved = false;
                    double slopeOne = GetSlope(leftLower.point, rightLower.point);
                    double SlopeTwo = GetSlope(leftLower.next.point, rightLower.point);
                    if (slopeOne < SlopeTwo)
                    {
                        leftLower = leftLower.next;
                        aMoved = true;
                    }
                }
                bool bMoved = true;
                while (bMoved)
                {
                    bMoved = false;
                    double slopeOne = GetSlope(leftLower.point, rightLower.point);
                    double SlopeTwo = GetSlope(leftLower.point, rightLower.previous.point);

                    if (slopeOne > SlopeTwo)
                    {
                        rightLower = rightLower.previous;
                        bMoved = true;
                        runAgain = true;
                    }
                }
            }
            leftUpper.next = rightUpper;
            rightUpper.previous = leftUpper;
            rightLower.next = leftLower;
            leftLower.previous = rightLower;
            mergedHull.leftUpper = leftUpper;
            mergedHull.rightUpper = rightUpper;
            mergedHull.leftLower = leftLower;
            mergedHull.rightLower = rightLower;

            return mergedHull;
        }


        public float GetSlope(PointF One, PointF Two)
        {
            return -((Two.Y - One.Y) / (Two.X - One.X));
        }

    }
}
