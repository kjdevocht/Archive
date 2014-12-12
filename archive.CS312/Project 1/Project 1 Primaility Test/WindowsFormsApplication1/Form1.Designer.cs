namespace WindowsFormsApplication1
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.input = new System.Windows.Forms.TextBox();
            this.output = new System.Windows.Forms.TextBox();
            this.k = new System.Windows.Forms.TextBox();
            this.solve = new System.Windows.Forms.Button();
            this.inputlbl = new System.Windows.Forms.Label();
            this.klbl = new System.Windows.Forms.Label();
            this.outputlbl = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // input
            // 
            this.input.Location = new System.Drawing.Point(112, 40);
            this.input.Name = "input";
            this.input.Size = new System.Drawing.Size(161, 20);
            this.input.TabIndex = 0;
            // 
            // output
            // 
            this.output.Location = new System.Drawing.Point(112, 115);
            this.output.Name = "output";
            this.output.Size = new System.Drawing.Size(304, 20);
            this.output.TabIndex = 3;
            // 
            // k
            // 
            this.k.Location = new System.Drawing.Point(112, 76);
            this.k.Name = "k";
            this.k.Size = new System.Drawing.Size(161, 20);
            this.k.TabIndex = 1;
            // 
            // solve
            // 
            this.solve.Location = new System.Drawing.Point(141, 141);
            this.solve.Name = "solve";
            this.solve.Size = new System.Drawing.Size(100, 23);
            this.solve.TabIndex = 2;
            this.solve.Text = "Solve";
            this.solve.UseVisualStyleBackColor = true;
            this.solve.Click += new System.EventHandler(this.solve_Click);
            // 
            // inputlbl
            // 
            this.inputlbl.AutoSize = true;
            this.inputlbl.Location = new System.Drawing.Point(76, 43);
            this.inputlbl.Name = "inputlbl";
            this.inputlbl.Size = new System.Drawing.Size(30, 13);
            this.inputlbl.TabIndex = 4;
            this.inputlbl.Text = "input";
            // 
            // klbl
            // 
            this.klbl.AutoSize = true;
            this.klbl.Location = new System.Drawing.Point(93, 79);
            this.klbl.Name = "klbl";
            this.klbl.Size = new System.Drawing.Size(13, 13);
            this.klbl.TabIndex = 5;
            this.klbl.Text = "k";
            // 
            // outputlbl
            // 
            this.outputlbl.AutoSize = true;
            this.outputlbl.Location = new System.Drawing.Point(48, 118);
            this.outputlbl.Name = "outputlbl";
            this.outputlbl.Size = new System.Drawing.Size(58, 13);
            this.outputlbl.TabIndex = 6;
            this.outputlbl.Text = "Is it Prime?";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(479, 312);
            this.Controls.Add(this.outputlbl);
            this.Controls.Add(this.klbl);
            this.Controls.Add(this.inputlbl);
            this.Controls.Add(this.solve);
            this.Controls.Add(this.k);
            this.Controls.Add(this.output);
            this.Controls.Add(this.input);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox input;
        private System.Windows.Forms.TextBox output;
        private System.Windows.Forms.TextBox k;
        private System.Windows.Forms.Button solve;
        private System.Windows.Forms.Label inputlbl;
        private System.Windows.Forms.Label klbl;
        private System.Windows.Forms.Label outputlbl;
    }
}

