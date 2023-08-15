#ifndef SHAPE
#define SHAPE

#include <set>
#include<list>
#include <iostream>
#include "point.h"

using namespace std;

class Shape {
 private:
  list<Point*> *hull;
  Shape* s1;
  Shape* s2;
  list<Point*> *computeHull(Shape *c1, Shape *c2 ); //done
  string id;
  
 public:
  static const int PAGE_DIMENSION = 200;
  static const int DEFAULT_MAX_SIZE = 20;
  
  Shape( Shape* s1,  Shape* s2 ); //done
  Shape( string id, bool randomize ); //done
  double *bounding_box(); //done
  
  bool isLeaf() const; //done
  Point* getCentroid() const; //done
  double getCentroidX() const; //done
  double getCentroidY() const; //done
  double area() const; //done
  list<Point*> *getPoints() const; //done 

  string getId() const; //done
  Shape* getFirstSubShape() const; //done
  Shape* getSecondSubShape() const; //done
  
  Shape* operator<<(Point *p); //done
  bool operator<( Shape& other ); //done
  Shape* operator()( double x_in, double y_in); //done
  Shape* searchTree(Shape* p, double x, double y); //done
  Shape& operator=( Shape& rhs);  //done
  Shape* operator,( Shape& other ); //done
  double operator-( Shape& rhs ); //done

  static void editSet(set<Shape*>& shapes, Shape* a, Shape* b);

  // THESE FUNCTIONS ARE NOT CLASS MEMBERS
  // THEY ARE DEFINED OUTSIDE OF THE CLASS
  // BUT REQUIRE ACCESS TO PRIVATE FIELDS
  friend ostream& operator<<(ostream&, Shape&); //done
  friend ostream& operator/(ostream&, Shape&); //done?
  friend Shape* reduce( set<Shape*>&  ); //done?
  friend bool ccw( Point& p1, Point& p2, Point& p3);

};

#endif
