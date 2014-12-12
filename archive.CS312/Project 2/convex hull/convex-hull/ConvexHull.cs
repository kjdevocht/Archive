using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Hull_Node;

namespace Convex_Hull
{
    class ConvexHull
    {
        public HullNode leftMost;
        public HullNode rightMost;


        public HullNode leftUpper;
        public HullNode rightUpper;
        public HullNode leftLower;
        public  HullNode rightLower;

        public ConvexHull(HullNode leftMost, HullNode rightMost)
        {
            this.leftMost = leftMost;
            this.rightMost = rightMost;
            this.leftUpper = null;
            this.rightUpper = null;
            this.rightLower = null;
            this.leftLower = null;
        }

    }
}
