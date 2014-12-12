using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;

namespace Hull_Node
{
    class HullNode
    {
        public PointF point;
        public HullNode next;
        public HullNode previous;

        public HullNode(PointF point)
        {
            this.point = point;
        }
    }
}
