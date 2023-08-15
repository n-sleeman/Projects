#include <iostream>
#include <algorithm>
#include <random>
#include <math.h>
#include <set>
#include "shape.h"
#include "poly_utils.h"
#include <climits>
using namespace std;

Shape::Shape(Shape* s1,  Shape* s2){
    this->s1 = s1;
    this->s2 = s2;
    string id1 = this->s1->id;
    string id2 = this->s2->id;
    if(id1.compare(id2) <= 0){
        this->id = id1+"-"+id2;
    }else{
        this->id = id2+"-"+id1;
    }
    this->hull = computeHull(s1, s2);
}

Shape::Shape(string id, bool randomize){
    this->id = id;
    this->s1 = NULL;
    this->s2 = NULL;
    if(randomize){
        this->hull = genTriangle();
    }else{
        this->hull = new list<Point*>();
    }
}

list<Point*> *Shape::computeHull(Shape* c1, Shape* c2){ 
    list<Point*> *points = c1->getPoints();
    list<Point*> *second = c2->getPoints();
    for( Point* p : *second ) {
        points->push_back( p );
    }
    int size = points->size();
    vector<Point*> pts(points->begin(), points->end());
    list<Point*> *newHull = new list<Point*>();

    double minX = INFINITY;
    int start = -1;
    //find left most point (minX)
    for(int i = 0; i < size; i++){
        if(pts[i]->x < minX){
            minX = pts[i]->x;
            start = i;
        }
    }
    //meat and potatoes (I wanna cry)
    int l = start;
    newHull->push_back(pts[l]);
    while(true){
        int q = (l + 1) % size;
        for(int i = 0; i < size; i++){
            if(i == l) continue;
            double d = direction(*pts[l], *pts[i], *pts[q]); 
            if(d > 0 || ((d == 0) && (point_distance(*pts[i], *pts[l]) > point_distance(*pts[q], *pts[l])))){
                q = i;
            } 
        }
        l = q;
        if(l == start) break;
        newHull->push_back(pts[q]); 

    }
    points->clear();
    second->clear();
    delete points;
    delete second;

    return newHull;
}

list<Point*> *genTriangle(){
    double x1, x2, x3, y1, y2, y3;
    double height = INFINITY;
    double width = INFINITY;
    while(height > Shape::DEFAULT_MAX_SIZE || width > DEFAULT_MAX_SIZE){
        std::random_device rd; 
        std::mt19937 gen(rd()); 
        std::uniform_int_distribution<> dis(0, PAGE_DIMENSION); 
        x1 = dis(gen);
        x2 = dis(gen);
        x3 = dis(gen);
        y1 = dis(gen);
        y2 = dis(gen);
        y3 = dis(gen);

        double maxX = max(max(x1, x2), x3);
        double maxY = max(max(y1, y2), y3);
        double minX = min(min(x1, x2), x3);
        double minY = min(min(y1, y2), y3);

        height = maxY-minY;
        width = maxX-minX;
    }

    Point *one = new Point(x1, y1);
    Point *two = new Point(x2, y2);
    Point *three = new Point(x3, y3);
    list<Point*> *ptrpts = new list<Point*>();
    ptrpts->push_back(one);
    ptrpts->push_back(two);
    ptrpts->push_back(three);

    return ptrpts;
}

double *Shape::bounding_box(){
    list<Point*> *points = this->getPoints();
    double minX = PAGE_DIMENSION;
    double minY = PAGE_DIMENSION;
    double maxX = -1;
    double maxY = -1;
    for(list<Point*>::iterator it = points->begin(); it != points->end(); it++){
        double curX = (*it)->x;
        double curY = (*it)->y;
        if(curX > maxX){
            maxX = curX;
        }else if(curX < minX){
            minX = curX;
        }
        if(curY > maxY){
            maxY = curY;
        }else if(curY < minY){
            minY = curY;
        }
    }
    points->clear();
    delete points;
    double* arr = new double[4];
    arr[0] = minX;
    arr[1] = minY;
    arr[2] = maxX;
    arr[3] = maxY;
    return arr;
}

double Shape::area() const{ 
    int size = hull->size();
    double area = 0.0;
    list<Point*> *points = this->getPoints();
    list<Point*>::iterator iti = points->begin();
    for(int i = 0; i < size; i++, iti++){
        list<Point*>::iterator itj = points->begin();
        int j = (i + 1) % size;
        advance(itj, j);
        area += ((*iti)->x * (*itj)->y);
        area -= ((*itj)->x * (*iti)->y);
    }
    points->clear();
    delete points;
    return abs(area)/2.0;
}

list<Point*> *Shape::getPoints() const{
    list<Point*> *points = new list<Point*>(*(this->hull));
    return points;
}

Point* Shape::getCentroid() const{
    Point* p = new Point(getCentroidX(),getCentroidY());
    return p;
}

double Shape::getCentroidX() const{
    int total = hull->size();
    double sum = 0;
    list<Point*> *points = this->getPoints();
    for(Point* p : *points){
        sum += p->x;
    }
    points->clear();
    delete points;
    return sum/total;
}

double Shape::getCentroidY() const{
    int total = hull->size();
    double sum = 0;
    list<Point*> *points = this->getPoints();
    for(Point* p : *points){
        sum += p->y;
    }
    points->clear();
    delete points;
    return sum/total;
}

bool Shape::isLeaf() const{
    return s1 == NULL && s2 == NULL;
}

string Shape::getId() const{
    return id;
}

Shape* Shape::getFirstSubShape() const{
    return s1;
}

Shape* Shape::getSecondSubShape() const{
    return s2;
}

Shape* Shape::operator<<(Point* p){
    this->hull->push_back(p);
    return this;
}

bool Shape::operator<(Shape& other){
    return this->area() < other.area();
}

Shape* Shape::operator()(double x_in, double y_in){
    if(convexPolyContains(hull, x_in, y_in)){
        return searchTree (this, x_in, y_in);
    }
    return NULL;
}

Shape* Shape::searchTree(Shape* s, double x, double y){
    Shape* curShape = s;
    if(!(s->isLeaf())){  
        bool inSubOne = convexPolyContains(s->s1->hull, x, y);
        bool inSubTwo = convexPolyContains(s->s2->hull, x, y);
        if(inSubOne || inSubTwo){
            if(inSubOne && inSubTwo){
                Shape* subOne = searchTree(s->s1, x, y);
                Shape* subTwo = searchTree(s->s2, x, y);
                if(subOne->area() <= subTwo->area()){
                    curShape = subOne;
                }else{
                    curShape = subTwo;
                }
            }else if(inSubOne){
                curShape = searchTree(s->s1, x, y);
            }else{
                curShape = searchTree(s->s2, x, y);
            }
        }
    }
    return curShape;
}

Shape& Shape::operator=(Shape& rhs){
    this->s1 = rhs.s1;
    this->s2 = rhs.s2;
    this->id = rhs.id;
    this->hull = rhs.hull;
    return *this;
}

Shape* Shape::operator,(Shape& other){
    return new Shape(this, &other);
}

double Shape::operator-(Shape& rhs){  
    list<Point*> *these = this->getPoints(); 
    list<Point*> *others = rhs.getPoints();
    double maxDubminDist = INFINITY;
    for(list<Point*>::iterator iti = these->begin(); iti != these->end(); iti++){
        Point* p1 = *iti;
        for(list<Point*>::iterator itj = others->begin(); itj != others->end(); itj++){
            Point* p2 = *itj;
            double dist = point_distance(*p1,*p2);
            if(dist < maxDubminDist){
                maxDubminDist = dist;
            }
        }
    }
    these->clear();
    others->clear();
    delete these;
    delete others;
    return maxDubminDist;
}

ostream& operator<<(ostream& stream , Shape& shape){
    list<Point*> *points = shape.getPoints();
    string build = "";
    for(list<Point*>::iterator it = points->begin(); it != points->end() ;it++){
        string x = to_string((*it)->x);
        string y = to_string((*it)->y);
        build += ", (" + x + ", "+ y + ")";
    }
    stream << "[ " << shape.id << build << " ]" ;
    points->clear();
    delete points;
    return stream;
}   

ostream& operator/(ostream& stream, Shape& shape){
    list<Point*> *points = shape.getPoints();
    string build = "";
    for(list<Point*>::iterator it = points->begin(); it != points->end() ;it++){
        string x = to_string((int)(*it)->x);
        string y = to_string((int)(*it)->y);
        build += x + " " + y + " ";
    }
    list<Point*>::iterator it = points->begin();
    build += to_string((int) (*it)->x) + " " + to_string((int) (*it)->y) + " ";
    if(shape.isLeaf()){
        stream << "<polyline points=\"" << build << "\" style=\"stroke:black;stroke-width:.1;stroke-opacity:.9;fill:orange;fill-opacity:.4\" name=\"" << shape.getId() << "\"/>" << endl;
    }else{
        stream << "<polyline points=\"" << build <<  "\" style=\"stroke:blue;stroke-width:.15;stroke-opacity:.25;fill:green;fill-opacity:.05;\" name=\"" << shape.getId() << "\"/>" << endl;
        stream / *(shape.getFirstSubShape()) << endl;
        stream / *(shape.getSecondSubShape()) << endl;
    }
    points->clear();
    delete points;
    return stream;
}

Shape* reduce(set<Shape*>& shapes){
    std::set<Shape*>::iterator iti;
    std::set<Shape*>::iterator itj;
    while(shapes.size() > 1){
        iti = shapes.begin();
        Shape* a;
        Shape* b;
        double minDist = INFINITY;
        double minArea = INFINITY;
        double curDist;
        double curArea; 
        while(iti != shapes.end()){
            itj = next(iti,1);
            while(itj != shapes.end()){
                curDist = (**iti) - (**itj); 
                curArea = (*iti)->area() + (*itj)->area(); 
                if(curDist < minDist || (curDist == minDist && curArea < minArea)){
                    a = *itj;
                    b = *iti;
                    minDist = curDist;
                    minArea = curArea;
                }
                itj++;
            }
            iti++;
        }
        Shape::editSet(shapes, a, b);

    }
    return *(shapes.begin());
}

void Shape::editSet(set<Shape*>& shapes, Shape* a, Shape* b){
    shapes.erase(a);
    shapes.erase(b);
    Shape* c = ((*a),(*b)); 
    shapes.insert(c);
}
