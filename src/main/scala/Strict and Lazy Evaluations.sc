/*
Lazy evaluation is an important idea in the FP world.

Strict -> evaluate the expression now.
Lazy -> evaluate it on the first use.
 */

/*
Strict evaluation

Strict behaviour
i) Variable assignment
ii) Function parameters
iii) Higher order functions

First two scenarios are simple and have been using it for a long time
 */

// First scenario: Variable assignment

def double(m:Int): Int = {
  return m * 2
}

def triple(m:Int): Int = {
  return m * 3
}

val s = double(30)/triple(10)
//initalizing the value s with the result of an expression

/*
Like most of the languages, Scala is a strict language.

So value for s is evaluated immediately.

Result of the expression is 2, so s gets assignment of 2
 */

println(s)
/*
When printing the s,
it will then not call the functions again,
because the expression is strict,
and Scala evaluates it just once.

So s is initialized just once,
and then will be reused.

 */

//Second scenario: Function parameters
/*
What will happen if pass an expression to a function?

 */

def twice(i:Int): Int = {
  return i + i

}
/*
Twice is a function, it takes an integer,
and returns by adding it to itself
 */

//call it and pass an expression as a parameter
twice(double(30)/triple(10))
/*
Scala evaluates the expression before it
can pass the result to the function parameter.

So the parameter i is initialized in the beginning,
and Scala never reevaluates it inside the function.

The i is evaluated before it is used.

Variable assignment and function parameters
can be seen in many programming languages.

But this is not the case with higher order functions
 */

def twiceHigher(f: => Int): Int = {
  return f + f

}
/*
Here Scala is evaluating f twice
If use f 10 times in function,
Scala will evaluate it 10 times

While such a behaviour might be necessary for a parameter
that is not a simple value, but a function,
if the parameter function is pure,
and it always returns the same value,
the repeated execution is unnecessary,
and may cause a performance problem.

So the way to avoid it is:
Cache the parameters in a val
 */
def twiceHigherCached(f: => Int): Int= {

  val i = f
  return i + i
}
/*
evaluating f once,
and caching the value as i
then every time need f, use the i

this caching will save from calling f several times

f also goes through a strict evaluation
Scala evaluates it immediately, as soon as refer it
 */

twiceHigherCached({double(30)/triple(10)})

/*
Strict evaluation summarization

1. Default evaluation in Scala is strict.
- If using a val to hold the value of an expression,
it will evaluate only once.
But the evaluation is immediate.

2. Scala evaluates function parameters
before passing the value to a function.
Hence they are evaluated only once.

3. If the parameter value is a function (In the case of higher order functions),
Scala evaluates it inside the body of a function on every use.
- Higher order function is the exception to the rule
of only once evaluation.
-When you pass a function to a higher order function as a parameter,
Scala will evaluate the expression on the use of a parameter,
instead of evaluating it immediately.
-So Scala evaluates the parameter function on every use.

4. If don't want multiple evaluations of a function's value,
can cache it.
 */