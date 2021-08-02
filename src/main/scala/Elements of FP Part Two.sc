/*
Immutability
Means unable to change.
In FP, create values or objects by initializing them.
Then we use them, but we don't change their values or their state.
If we need, we create a new one, but we don't modify the existing object's state.

Scala has two types of variables,
var, which stands for variable. Can change var.
val, which stands for value. Can't change val.

 */

var s = "Hello."
println(s)

s = "Hi."
println(s)

//val is a constant,
//which means once initialized, can't change it.
val a = "One"
println(a)
//can't reassign val. Unlike var.

/*
Since Scala is a hybrid language, it supports var and val both.
But when using Scala as FP language, recommend to use val instead of var.

Using val will force you to create programs using only the constants.
Immutability = program using constants.
 */
/*
Two questions
1. How can we program without a variable?
2. What are the benefits?
*/

/*
2. Benefits
i) Immutability helps to adopt a mathematical approach.
- FP has it's origin from the mathematical functions.
In a real world, objects do change their state.
But in mathematics, objects don't change their state.

i.e.
V = 3+1 vs V = sum(3,1)
Apply sum function, pass 2 integer objects,
and function returns new integer object.

A mathematical function never changes the value of the
existing object.
In above example, the sum function doesn't modify the
input objects.
However, it returns a new object.

FP is more inclined towards using functions in a mathematical
sense, and using them to solve our problems.

The immutability helps us to take a mathematical approach,
and create pure functions.

ii) Immutability helps to avoid various problems
- Immutable objects are more thread safe.

Immutability is a big thing in a multithreaded application.

It allows a thread to act on an immutable object,
without worrying about the other threads,
because it knows that no one is modifying the object.

So the immutable objects are more thread safe than the
mutable objects.

For concurrent programming, immutability makes life simpler.

A more interesting example comes from data pipelines.

We often use immutability in data engineering process.

You have a data set, you are requested to perform a
data quality operation.

1001, yyyyy, zzzzz, Mon Jul 10 2017 0905 IST
1002, yyyyy, zzzzz, Sun Jul 9 2017 2036 PDT
1003, yyyyy, zzzzz, Sun Jul 9 2017 2337 EDT
1004, yyyyy, zzzzz,
1005, yyyyy, zzzzz, Mon Jul 10 2017 0641 EEST
1006, yyyyy, zzzzz, Mon Jul 10 2017 0539 CEST
1007, yyyyy, zzzzz, Mon Jul 10 2017 0440 BST

Want to
a) Remove records where date column is blank
b) Change date format of all rows into a consistent time zone

Method 1:
Delete all rows with a blank date. Then update all rows to
a consistent date format.

Assume did that, and a few days later, identified a bug
in the code.

Thus would have lost
i) original time component
ii) original time zone info
in the data.

Can always fix your code and redeploy it, but what about data?
Modified the original data, and lost original values.
Also deleted some records as well.

The bug in the code has corrupted the data.
It's no longer reliable, and now have no way to fix the data problem.

Method 2 (better):
Take the immutable approach, and do not modify the original dataset.

Write one function, that creates a new dataset T1,
by filtering out all records with a blank date.

Then a second function to convert the date into a
consistent format, and save it as a new dataset T2.

Later when identify a bug in code, just execute the
new function on the original and immutable dataset.

So immutable approach helps simplify the solution,
and avoid many problems. Even human errors.

So that's why when using Scala best to use val,
and avoid var.
*/

/*
1. How program without a variable?
FP like Scala provide many language constructs to achieve that.

i.e.
 */

def iFactorial(n:Int):Int = {
  var i = n
  var f = 1
  while(i>0) {
    f = f * i
    i = i - 1
  }

  return f
}

iFactorial(5)

/*
Is iFactorial a pure function?
Pure function:
1. The input solely determines the output
2. The function doesn't change it's input
3. The function doesn't do anything else except
computing the output
*/

/*
how to write the function using val

Recursion
Recursion is a programming technique,
in which a function calls itself.

Can replace most of the loops with a recursion.
Recursion is used extensively in functional programming.

 */

def rFactorial(n:Int): Int = {
  if (n<=0)
    return 1
  else
    return n * rFactorial(n-1)
}
rFactorial(5)

//Can convert loops into recursion and avoid mutation.
//so can replace loops with recursion and avoid using vars.

/*
How program other stuff without variable?
Can fix loops but there are many other things.

Can create new value for each new state.
Like the data quality check example.
Is not changing the original dataset,
but create two new tables.

Can take the same approach and create new values,
instead of changing existing ones.

And that's perfectly fine.

But not taking an oath to keep everything immutable.
Mutability may have it's definite advantages.

80% Pure and Immutable Core
20% Outer layer Impure and Mutable

Free to choose what suits best for the given problem.

Immutability is a powerful thing. It simplifies the solution,
and avoids many problems.

So it is the default approach for FP.

A Functional Programmer challenges every mutation,
and tries an immutable alternative.

 */

/*

Tail Recursion

What is a tail
List like (5, 4, 3, 2, 1, 0)

First element is the head,
and the rest is the tail.

Can remove the head, and create a new list.
(4, 3, 2, 1, 0)
Now 4 is the new head, and the rest of the list is the tail.

Looking at any recursive algorithm, it always works on a list.

When need to think about something recursively, can follow three steps:
1. Identify the list.
2. Implement the termination condition.
3. Compute the head and recurs with the tail.

The compute is the part where need to think.
Rest is all same for recursion implementation.


 */

/*
For the rFactorial function

The list is (5, 4, 3, 2, 1, 0).

The termination condition is 0.
To compute a factorial, can't multiply with 0.
So return 1.

The compute part is a multiplication of the head,
with the new head of the tail.
So recurs with the tail, to get the next head,
and multiply it with the current head.

But there is a catch here.
Can't multiply this head, until you discover the
next head from the next call.
So runtime environment will keep first head in the stack,
and make a new call.
And this goes on until it reaches termination.
Every call is waiting for the next call to complete.

5, 4, 3, 2, 1, 0

So the actual multiplication happens when reach the
termination condition, and the chain of calls start folding

5, 4, 3, 2, 1, returns 1
5, 4, 3, 2, returns 1
5, 4, 3, returns 2
5, 4, returns 6
5, returns 24
returns 120

So each recursive call requires a frame in the stack.

That's a big problem.
If recursion goes on for thousands of calls,
may run out of memory.
That's a significant limitation of recursion.

If implement same logic using loop,
these stack frames are not used.

 */

/*
Conclusion:
Is loop better than recursion?

If consider memory requirements and performance,
definitely yes.

So loops are more efficient than recursion.

Scala compiler knows this and tries to optimize the recursive calls.

But we have to redesign the recursion in a way,
that there are no unfinished operations for the next
recursive call.

That is where the tail recursion comes in.

Tail recursion is an optimization.

Tail recursion is a function call performed as the last action.

Which means the recursive call should be the last operation in the function.

Factorial function
def rFactorial(n:Int): Int = {
  if (n<=0)
    return 1
  else
    return n * rFactorial(n-1)
    // here it waits for the recursive call (rFactorial(n-1))
    // and then multiplies the result by n. (return n *)
    // so the multiplication is the last action.
}

To make it tail recursive, need to change it in a way,
that the recursive call becomes the final action,
instead of the multiplication.

In the old logic, the first multiplication happens,
when reach the termination condition,
and get 1 as the last number.

Instead of returning 1 at the termination,
we can take it in the beginning,
and perform the multiplication,
before the recursion call.

To implement this, need 2 input parameters.

 */
@scala.annotation.tailrec
def tFactorial(n:Int, f:Int):Int = {
  if(n<=0)
    return f
  else
    return tFactorial(n-1, n*f) //instead of saving info in stack, n * f calculates it in the parameter
  //recursive call is the last action
}
tFactorial(5, 1)
/*
When scala compiler recognizes it's a tail recursion,
it will optimize the function by converting it to a standard loop.

We will not realize the change, but the compiler will do it internally.

This optimization will overcome the performance and memory problem.

So there will be only one function call in the stack trace.
Because compiler converts tail recursion to a loop.
 */

/*
Instead of using 2 parameters for the tail recursion,
can also wrap it in a outer function.
 */

def oFactorial(i:Int):Int = {
  println("Called factorial for "+i)

  @scala.annotation.tailrec
  def tailFact(n:Int, f:Int):Int = {
    if(n<=0) f
    else tailFact(n - 1, n * f )
  }

  return tailFact(i, 1)
}
//here factorial takes one parameter
//and it internally calculates the factorial
//using the tailFact function.

oFactorial(5)

/*
Statement
A statement is the smallest, standalone element,
that expresses some action to be carried out.

 */

//this is a statement
println("Hello Scala")

/*
There is another common term, which is definition or declaration.
A definition is also a kind of statement.
It is a particular type of statement that defines something.
 */

//this is a definition or declaration
var definition = "Hello"

// a program is nothing but a sequence of statements

def myResult(m:Int): Unit = {
  var r = ""
  if (m >= 50) {
    r = "passed"
  }
  else {
    r = "failed"
  }
  println(r)
}

myResult(60)

/*
as per the myResult function,
can see the extended description of a program,

a program is nothing but a sequence of statements,
that modify some program state

In the myResult program,
have an if statement, and then, have a print statement

Both of these statements are modifying something

The if statement is changing the state of r,
and the print statement changes the state of the console.

Meanwhile, the r = .. is a definition.
 */

/*
The above is in imperative programming.

"Program is nothing but a sequence of statements
that modify some program state"

For the functional programming paradigm, the above description
is against functional programming.

In FP, we don't modify states.

In FP, we have definitions and statements.

But every statement should have the capability
to return a value.

That's the ground rule for functional statements.
 */

val a: Unit = println("Hey")
//So the println statement returns a unit

/*
The unit is like void in java,
but not exactly same as void.

Void means nothing, whereas unit has a value.

unit value is represented using this symbol ()


 */

/*
So every statement in Scala, and FP, can return soe value.

Even a Scala loop can return some value.

Since Scala is a functional programming language,
the statements in Scala can have a return value.

Since a Scala statement returns a value,
some people don't call them a statement.

They call them an expression.

So common to hear that Scala doesn't have statements,
but only expressions.
 */

/*
Benefits of statement returning a value.

1. Helps achieve immutability -
Using functional statements allows to
reduce number of variables in the code.

Removing variables from the code, helps to
achieve immutability.

Because if you don't have a variable, you don't need to mutate it.
 */

def myResultFunctional(m:Int): Unit = {
  if(m>=50) {
    println("passed")
  } else {
    println("failed")
  }
}

myResultFunctional(60)

/*
The if statement returns a value, so don't need variable.

The functional version of the code is concise,
and the returning statement
helps to eliminate variables and achieve immutability.

 */

