var g = 10

/*
this function doesn't have referential transparency
because
doesn't have: the input solely determines the output
doesn't have: the function doesn't do anything else
except computing the output (as it modifies an external var)

so not pure function
*/
def rt(i:Int):Int = {
  g = i + g
  return g
}

val v1 = rt(5)

val v2 = rt(5)
/*
Pure function:
1. The input solely determines the output
2. The function doesn't change it's input
3. The function doesn't do anything else except
computing the output

Referential transparency:
A function is referentially transparent if evaluating it
gives the same value for same arguments.

Can test the purity of a function using referential transparency.
 */

/*
Why Pure function
1. Encourage safe ways of programming
- small, simple, precise, easy to reuse, because
know they take input and give output based off input values

2. Composable or modular
- very common in functional programming to compose
many functions into a simple solution.
i.e. doThis(a).thenThis(b).andThenThis(c).finallyThis(d)
Can do with non pure functions too, but easy with pure,
because don't have side effects, and output depends only on input.

3. Easy to test
- Since no side effects, and output only depends on input,
test cases are straightforward.
Pass in the known value and assert for output value.

4. Memoizable
- Caching of deterministic functions. If know function is pure,
and need results again, can cache results of output.
But if have side effects, can't cache results for later use.

5. Pure functions can be lazy.
 */

/*
First class function

If can treat function as a value, then it's a
first class function.

So should be able to do everything with the function,
that you can do with a value.

You can
i) assign the function to a variable
- just as you can assign a value to a variable

ii) pass it as an argument to other functions
- just as you can pass a value as an argument to other functions

iii) return it as a value from other functions
-just as you can return a value from a function

If can do all these 3 things, then is first class function.

In Scala, all functions are first class functions by default.
 */

/*
Higher order function
Is a function that does at least one of the following
i) Takes one or more functions as arguments.

ii) Returns a function as it's result.
 */

//first class function by default
def f1 = println("Hello.")

//f2 is then a higher order function
def f2(f:Unit) = f

f2(f1)

//function that takes integer as input and returns it's double
def doubler(i:Int):Int = {return i*2}
//first class function by default

doubler(5)
doubler(5)

//assign function to a variable
val d = doubler _
d(5)
d(3)

//pass it to another function as an argument
//range
val r = 1 to 10
r.map(doubler)
r.map(d)

/*
Anonymous function
A standard function has a name, a list of parameters,
a return type, and a body.
If you don't give a name to a function, it is an
anonymous function.
 */
//syntax for anonymous function

(i:Int) => {i*2}:Int
//(list of parameters) => {body} :return type
//no name
//return type is optional,
//so if leave it, Scala will automatically infer it.

/*
A normal function uses = symbol before the body,
whereas an anonymous function uses =>,
which is also called function literal.
And this syntax is also called function literal syntax.

So how to call this function if doesn't have name?
Can assign it to a variable.
Then call it using the variable.

 */

val e = (i:Int) => {i*3}:Int
e(2)

/*
What is purpose of an anonymous function?

There might be scenarios where want to create
an inline function, for a one time usage.

And giving them a name doesn't make any sense,
because don't want to use them anywhere else.

In those cases, creating an anonymous function is quite
convenient.
 */

//higher order function
def getOps(c:Int) = (i:Int) => {

  val tripler = (x: Int) => {
    x * 3
  }
  val quadler = (x: Int) => {
    x * 4
  }

  if (c > 0)
    tripler(i)
  else quadler(i)
}
//getOps function takes an integer c
//if value of c is positive, it returns a tripler,
//else it returns a quadler

r.map(getOps(-4))
r.map(getOps(4))

//can also write getOps like this
def getOps2(c:Int) = (i:Int) => {
  if(c>0) {i*3}
  else {i*4}
}
//instead of defining the two tripler and quadler functions,
//and returning them later,
//simply use anonymous function body
//right at the place where it is needed
//so it is more easier to write and easy to understand.

r.map(getOps2(-4))
r.map(getOps2(4))

/*
May have scenarios where just want to create a function,
and use it right there.
Anonymous functions are there to allow you to do that.
 */

/*
Signature and body of a function code is a function literal
 */

/*
What is the benefit of passing functions around?

Abstraction is the main benefit of higher order functions.
Abstraction makes the application easily extendable.
When developing with a higher level of abstraction,
you communicate the behaviour, and less the implementation.

 */

var customers = Array("Mike","Zara", "Tom", "Peter")

//Want to send a message to all these clients.
//Most obvious method is with a loop, like this

for(i <- customers.indices) {
  println("Hi " + customers(i))
}

//instead of println, might want some other function
//like payment reminder

def remindPayment(x:String): Unit = println("Payment reminder for "+x)

for(i <- customers.indices) {
  remindPayment(customers(i))
}
//for each customer, remind payment

/*
can also use higher order function
forEach(customers, remindPayment)

which is more precise, easy to understand, and more reusable

can reuse the same function to send payment to all vendors
forEach(vendors, sendPayment)

or can use same function to invite all customers for an event.
forEach(friends, sendInvitation)
 */

//first parameter is array of strings
//second parameter is a function
def forEach(a:Array[String], f:String => Unit): Unit = {
  var i = 0

  //loop through the array and call the function for each element
  for(i <- a.indices) {
    f(a(i))
  }
}

forEach(customers,remindPayment)

def greet(x:String):Unit = println("Hi " + x)

forEach(customers,greet)

//scala provides iterator method for every collection
//so don't need implement forEach function
customers foreach remindPayment
//take customers, for each customer, remind payment

