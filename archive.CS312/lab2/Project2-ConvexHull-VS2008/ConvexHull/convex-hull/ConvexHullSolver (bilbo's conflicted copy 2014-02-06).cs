using System;
using System.Collections.Generic;
using System.Text;

namespace _2_convex_hull
{
    class ConvexHullSolver
    {
        class PointComparer : IComparer<System.Drawing.PointF>
        {
            public int Compare(System.Drawing.PointF x, System.Drawing.PointF y)
            {
                //compare distance between two points
                float result = (x.X * x.X + x.Y * x.Y) - (y.X * y.X + y.Y * y.Y);
                return (int) result;
            }
        }
        public void Solve(System.Drawing.Graphics g, List<System.Drawing.PointF> pointList)
        {
            pointList.Sort(new PointComparer()); //pass in arguments to make sure it sorts by x then by y
            dividePoints(pointList);
        }


        List<System.Drawing.PointF> dividePoints(List<System.Drawing.PointF> Q)
        {
            List<System.Drawing.PointF> LQ;
            List<System.Drawing.PointF> RQ;
            List<System.Drawing.PointF> newQ = new List<System.Drawing.PointF>();
	        if(Q.Count > 2)
	        {
                double half = Q.Count/2;
                int bottomHalf = (int)Math.Ceiling(half);
                int top = Q.Count - bottomHalf;
                LQ = Q.GetRange(0, bottomHalf);//round up on upperbound
		        RQ = Q.GetRange(bottomHalf, top); //round up on lowerbound
		        LQ = dividePoints(LQ);
		        RQ = dividePoints(RQ);
	        }
	        else
	        {
		        return Q;
	        }
            if(LQ.Count>2 && RQ.Count>2)
            {
                int i = 0;
                int j = 0;
                for( int k = i; k<LQ.Count-1; k++)
                {
                    if(LQ[i].Y < LQ[i + 1].Y)
                    {
                        newQ.Add(LQ[i]);//because of the way the list is ordered this is on the left side of hull and must be kept
                        i++;
                    }
                    else
                    {
                        break;
                    }
                }
                //You have reached the highest point on the left side of the hull
                for(int k = j; k<RQ.Count-1; k++) //finds the highest point on the right hull
                {
                    if(RQ[j].Y < RQ[j + 1].Y)
                    {
                       j++;//these point wont be in the new hull so just iterate past them i.e. through them away
                    }
                    else
                    {
                        break;
                    }

                }

                //you have reached the highest point on the right hull
                if (isAbove(LQ[i + 1], LQ[i], RQ[j]))//this means that lq and lq+1 have the same y value
                {
                    newQ.Add(LQ[i]);
                    newQ.Add(LQ[i + 1]);//Both of these points must be in the hull because the right side is lower then the left
                    i++;//update i to point at the point on the left hull used in the top common tangent
                    while (isAbove(RQ[j + 1], LQ[i], RQ[j]))//looking for the top common tangent 
                    {
                        j++;//because j+1 is above j, j cannot be the top common tangent so through this point away and try the next point
                    }
                    newQ.Add(RQ[j]);
                    j++;//j+1 is not above j so j is now at the highest y access and has no points above its slope with LQ.at(i)
                }
                else//the right hull must be equal or higher to the left hull
                {
                    newQ.Add(LQ[i]);
                    newQ.Add(RQ[j]);
                    i++;
                    i++;
                }



                //At this point the top common tangent has been found and most of the left side of the hull has
                //been added to the new merged hull
                //This next while loop is adding the right side and looking for the lowest point on the right
                for(int k = j; k<RQ.Count-1; k++) 
                {
                    if (RQ[j].Y > RQ[j + 1].Y)
                    {
                        newQ.Add(RQ[j]);//because it is bigger it cannot be the lowest point so add it as part of the right side of the hull
                        j++;
                    }
                    else
                    {
                        break;
                    }
                }

                //you should now be at the lowest point on the right
                for(int k = i; k<LQ.Count-1; k++)  //looking for the lowest point on the left hull
                {
                    if (LQ[i].Y > LQ[i + 1].Y)
                    {
                        i++;//these point are on the inside of the new hull so throw them away
                    }
                    else
                    {
                        break;
                    }
                    
                }

                //you have reached the lowest point on the left hull
                if (isBelow(RQ[j + 1], LQ[i], RQ[j]))//this means that RQ and RQ+1 have the same y value
                {
                    newQ.Add(RQ[j]);
                    newQ.Add(RQ[j + 1]);//Both of these points must be in the hull because the left side is lower then the right
                    //i++;//update i to point at the point on the left hull used in the top common tangent
                    while (isBelow(LQ[i + 1], LQ[i], RQ[j]))//looking for the bottom common tangent 
                    {
                        i++;//because i+1 is below i, i cannot be the bottom common tangent so throw this point away and try the next point
                    }
                    newQ.Add(LQ[i]);
                    i++;//i+1 is not below i so i is now at the lowest y access and has no points below its slope with RQ.at(j)
                }
                else//the right hull must be equal or higher to the left hull
                {
                    newQ.Add(RQ[j]);
                    newQ.Add(LQ[i]);
                    i++;
                    j++;
                }

                //finish looping throught the rest of the left hull
                for (int k = i; k < LQ.Count-1; k++)
                {
                    newQ.Add(LQ[k]);
                }
            }
            else
            {
                foreach(System.Drawing.PointF point in LQ)
                {
                    newQ.Add(point);
                }

                foreach (System.Drawing.PointF point in RQ)
                {
                    newQ.Add(point);
                }
            }

	        //The left and the right hulls should be merged now
	        return newQ;
        }

        bool isAbove(System.Drawing.PointF R1, System.Drawing.PointF L, System.Drawing.PointF R2)
        {
            int sign = ((L.X - R2.X) >= 0 ? 1 : -1);
            return Math.Abs((L.Y - R2.Y) * (R1.X - L.X) + (L.Y - R1.Y) * (L.X - R2.X)) * sign > 0;
        }

        bool isBelow(System.Drawing.PointF L1, System.Drawing.PointF L2, System.Drawing.PointF R)
        {
            int sign = ((L2.X - R.X) >= 0 ? 1 : -1);
            return Math.Abs((L2.Y - R.Y) * (L1.X - L2.X) + (L2.Y - L1.Y) * (L2.X - R.X)) * sign > 0;
        }
    }
}
