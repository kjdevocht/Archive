/* Kevin DeVocht Assingment 21 exercise 1. Write an essay of about 
800 words about what the process of programming is. Walking through 
an example problem might work. If you need one, try "You are about 
to go to the temple, but FamilySearch is down; how can you use a 
program to organize your ancestors into categories based on what 
ordinances need to be done for them, starting with the closest 
(dead over a year) relatives first. Make a data definition and 
describe which functions you’d need."*/

#include <stdio.h>
#include <math.h>
#include <string.h>

/*
The first step to solve a problem is to find out what you want the end 
result to be.  When you have decided what you want, you can then decide 
what you will need to reach that end result.  This is where you decide 
if mutation will be used or not.  This is also when you decide what type 
of data structures you will need.  After you have developed all the data 
structures that you need it is usually a good idea to compile the program 
to make sure there are no errors at this point (it makes fixing real problems 
in your program easier later on).  Once you have all the data structures 
correctly designed it is time to move on to the first function.  When you 
decided what the end result should be, you can usually see what at least one 
function should be, not necessarily what it should do.  This is a good place 
to start.  The contract statement is great to help decide what the function 
should return and what arguments the function needs to work.  Next the purpose 
statement helps you to put into writing what you want the function to do.  If 
you are having hard time writing it down it might mean you don’t really know 
what the function should be doing.  That is why it is important that you do 
the purpose statement before working on the function.  If you don’t know what 
the function should be doing it is hard to get the function working properly.  
Now that you have your contract statement it is really easy to create the 
function.  You know what it should return, the name of the function and what 
arguments the function needs.  Now that you know the name of the function, 
what arguments the function needs, and what it will return, you are ready to 
write some test cases for your function.  When writing the test cases, make sure 
to include all possibilities that you can think of including a zero or empty 
input.  Sometimes you can do this in as few as three tests. Other times it takes 
several more test to cover all possibilities.  After writing test cases, go back 
inside the function and use those tests to help you develop the code inside the 
function.  I usually begin by using the simplest test that does not return zero 
or empty.  I have found that when I do this I can focus on the real problem 
instead of getting overwhelmed trying to handle too many pieces.  You develop the 
code inside the function by seeing what the starting values of all the arguments 
and variables are and comparing them to what the end values should be.  As you do 
this for the different test cases, a few possibilities will become apparent.  You 
might need an auxiliary function to help reach the final answer.  This is often 
the case when dealing with compound data because you can’t look too deep at each 
piece of information.  You might be able to generalize the code inside the 
function or you might distinguish different results.  This same process is 
repeated for each additional function needed.  Sometimes you will know that you 
will need more than one function from the very beginning.  Accumulator style 
programing is like this.  You know you will use a wrapper function and then the 
accumulator function.  The process is slightly different when testing a function 
that uses mutation but the same general concept applies.  You must know the 
initial values for all parts that will be affected.  Then you must know what the 
results for those values will be.  Often nothing is returned.  Values are just 
changed inside the function.  Make sure that you can test each function 
independently from anything else if possible.  This makes it much easier to 
identify mistakes problems with that particular function.  Often simple 
syntactical errors disguise real underlying problems with the program and prevent 
you from being able to fix the real problem.  That is why it is so important to 
make sure your program is set up properly before working on the functions and 
also whenever you reach a point where you can test what you already have written 
before moving on.   If a program becomes really big and or complex it helps to 
draw the program out so that you can visually see what is going on.  That’s 
pretty much the whole process.  If you can break the problem up into smaller 
more manageable pieces it is first, easier to actually write the code for the 
program but also easier to solve the problem and figure out what must be done.  
A whole program can seem very impossible, but when broken down into smaller 
pieces you can have most of your pieces working and know where to look for the 
problem rather than one giant lump of non-working garbage with no idea where to 
even begin.
*/



int main ()
{
	printf("Stay on target. We're too close!  Stay on target!");
}