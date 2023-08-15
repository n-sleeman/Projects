//Simulates 100 prisoners problem with random choice or optimal choice (default)
//Comandline args flags: -t (integer) controls number of trials & -w (integer) controles width of histogram
// default #: -t 10000 & -w 200
package practice;
import java.util.*;
public class OneHundredPrisoners{
    public static final int ONE_HUNDRED = 100;
    public static void main(String [] args){
        int tTrials = 10000;
        int width = 200;
        for(int i = 0; i<args.length; i++){
            if(args[i].equals("-t")){
                tTrials = Integer.parseInt(args[i+1]);
            }else if(args[i].equals("-w")){
                width = Integer.parseInt(args[i+1]);
            }
        }
        runSim(tTrials, width);
    }

    public static char end(double d){
        if(.05 <= d && d < .5){
            return '-';
        }else if(.5 <= d && d < 1){
            return '=';
        }
        return '\0';
    }

    public static void runSim(int totalTrials, int width){
        int optW = 0,ranW = 0;
        int [] randomDist = new int[ONE_HUNDRED];
        int [] optimalDist = new int[ONE_HUNDRED+1];
        for(int i = 0; i<totalTrials; i++){
            int ranSuccesses = runRan();
            int optSuccesses = runOpt();
            randomDist[ranSuccesses]++;
            optimalDist[optSuccesses]++;
            if(ranSuccesses == ONE_HUNDRED) ranW++;
            if(optSuccesses == ONE_HUNDRED) optW++;
        }
        double percentageOpt = (double)optW/totalTrials;
        double percentageRan = (double)ranW/totalTrials;
        System.out.format("Estimated probability of release: %.6f ("+totalTrials+" trials with optimal strat)\n",percentageOpt);
        for(int i = 0; i < optimalDist.length; i++){
            double q = (double) optimalDist[i]/totalTrials;
            System.out.format("Prisoners who found their number: %2d -- %.6f |",i,q);
            for(int j = 0; j< (int)(q*width); j++){
                System.out.print("#");
            } 
            System.out.println(end((q*width) - ((int)(width*q))));
        }
        System.out.println();
        System.out.format("Estimated probability of release: %.6f ("+totalTrials+" trials with random picks)\n",percentageRan);
        for(int i = 0; i < randomDist.length; i++){
            double q = (double)randomDist[i]/totalTrials;
            System.out.format("Prisoners who found their number: %2d -- %.6f |",i,q);
            for(int j = 0; j< (int)(q*width); j++){
                System.out.print("#");
            } 
            System.out.println(end((q*width) - ((int)(width*q))));
        }
    }

    public static int runOpt(){
        int [] boxes = new int[ONE_HUNDRED];
        int found = 0;
        //randomizing boxes
        List<Integer> bag = new ArrayList<Integer>();
        for(int i = 0; i<ONE_HUNDRED; i++){
            bag.add(i);
        }
        for(int i = 0; i<ONE_HUNDRED; i++){
            boxes[i] = bag.remove((int)(Math.random()*bag.size()));
        }
        //sequence strat
        for(int i = 0; i<ONE_HUNDRED; i++){
            int nextBox = i;
            for(int j = 0; j<(ONE_HUNDRED/2); j++){
                if(boxes[nextBox] == i){
                    found++;
                    break;
                }else{
                    nextBox = boxes[nextBox];
                }
            }
        }
        return found;
    }

    public static int runRan(){
        int [] boxes = new int[ONE_HUNDRED];
        int found = 0;
        //randomizing boxes
        List<Integer> bag = new ArrayList<Integer>();
        for(int i = 0; i<ONE_HUNDRED; i++){
            bag.add(i);
        }
        for(int i = 0; i<ONE_HUNDRED; i++){
            boxes[i] = bag.remove((int)(Math.random()*bag.size()));
        }
        
        //random picking
        for(int i = 0; i<ONE_HUNDRED; i++){
            //random picks per prisoner lol
            for(int j =0; j<ONE_HUNDRED; j++){
                bag.add(i);
            }
            for(int j = 0; j<(ONE_HUNDRED/2); j++){
                if(boxes[bag.remove((int)(Math.random()*bag.size()))] == i){
                    found++;
                    break;
                }
            }
        }
        return found;
    }
}