package partial_function

/*
 * Partial function (math.) - it's opposed to "total" functions.
 *
 * So, partial function is a one where there is possible to pass such arguments,
 * where the function is not defined / where it does not have sense.
 *
 * Like: (x:Int, d:Int) => Int => x/d. When d = 0 is applied, then the value of the function does not exist
 *
 * #partial-function
 *
 */
object PartialFunctionTest extends App {

  // 1, let's define a function that divides 10 by d
  def divide10By(d:Int):Int = { // this function does not exists when d = 0
    10/d
  }

  // test it (1) :

//  divide10By(0) // will fail - "ArithmeticException: / by zero"


  // 2, the option to define this function as "PartialFunction" to expect 0 argument that leads
  val divide2 = new PartialFunction[Int, Int] {  // first param represents argument, 2nd - returning value

    def apply(d: Int): Int = 10 / d   // apply has all our function's logic

    def isDefinedAt(d: Int): Boolean = d != 0

  }

  // test it (2) :

   println ( divide2.isDefinedAt(2) ) // "true"
   println ( divide2.isDefinedAt(0) ) // "false"

//  divide2(0)  // still fail (of course, because nobody uses 'isDefinedAt()' method )

 // 2.1 let's improve our 2nd try. To apply some case-magic

  def divide3: PartialFunction[Int,Int] = {  // Note ! for now we are passing anonymous function with "case"
    // this make birth of anonymous function
    case d:Int if d!=0 => 10/0                // thus we let built-in apply(f:Int=>Int) method create PartialFunction impl
                                              // based on anonymous function we are passing
  }

  // what about "isDefinedAt" ?
  println ( divide3.isDefinedAt(2) )  // "true"  ! So, passing anonymous function as an argument,
  println ( divide3.isDefinedAt(0) )  // "false" ! makes "isDefinedAt()" function to be implemented, based on
                                                 // case-anonymous-function, including all if-guard


//  divide3(0)  // scala.MatchError: 0 (of class java.lang.Integer) This already different case-related error! That's good!

 // 2.2 Let' use case-advantage, to let use pass not only "Int", but any argument
 def divide4: PartialFunction[Any,Int] = {  // Note ! for now we are passing anonymous function with "case"
   // this make birth of anonymous function
   case d:Int if d!=0 => 10/0                // thus we let built-in apply(f:Int=>Int) method create PartialFunction impl
   // based on anonymous function we are passing
 }

 divide4.isDefinedAt("hello") // how we can test against String .. or Any other type  - it returns "false"
// divide4("hell0")   // scala.MatchError: hell0 (of class java.lang.String)  - case related error

 // So, what we need is a function that could accept partial function a argument, and before applying it,
 // first check isDefinedAt() method
 // TODO:


 // 3. Example of PartialFunction from Scala collections
 val list = List(1,2, "a", "b")

// list.map{ case x:Int => x+1 }  // map accepts anonymous (not Partial) Function,
                                // that's why it fails, because the "case" does not handle / expect String type

 //but if we  use a method that accept PartialFunction as argument, then ...
  val list2 = list.collect{case x:Int => x+1} //

  list2 foreach( print(_) )  // 23

}