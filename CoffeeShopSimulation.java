/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshopsimulation;
import java.util.Random;
/**
 *
 * @author Bayram
 */
public class CoffeeShopSimulation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        System.out.println("testing max: " + max(200,11));
//        System.out.println("testing max: " + max(200,300));
//        System.out.println();
        
        
        int n= 100;int a = 3; int b = 2; int c = 4; int d = 5;
        Customer[] customers = makeArrayOfCustomers(n, a, b, c, d, 1);
        
        int cumulative_time = 0;
        
        int q_duration_total = 0;
        int q_duration_indiv = 0;
        int q_duration_max = 0;
        int q_size_total = 0;
        int q_size_max = 0;
        int next_s;
        
        Server [] servers = new Server[]{new Server(1), new Server(2),new Server(3)};

        //assign(customers[0],servers[0],cumulativeTime);
        cumulative_time += customers[0].duration_before;
        servers[0].cID = customers[0].id;
        servers[0].cIn = cumulative_time;
        servers[0].cOut = cumulative_time + customers[0].duration_ordering;
        
        //assign(customers[1],servers[1],cumulativeTime);        
        cumulative_time += customers[1].duration_before;
        servers[1].cID = customers[1].id;
        servers[1].cIn = cumulative_time;
        servers[1].cOut = cumulative_time + customers[1].duration_ordering;
        
        //assign(customers[2],servers[2],cumulativeTime);
        cumulative_time += customers[2].duration_before;
        servers[2].cID = customers[2].id;
        servers[2].cIn = cumulative_time;
        servers[2].cOut = cumulative_time + customers[2].duration_ordering;
              
        
        //System.out.print("cumulative time after 3rd cust: " + cumulativeTime + "\t");        

        for(int i = 3; i < n; i++){
            next_s = checkAvailableServers(servers);
            cumulative_time += customers[i].duration_before;
//            q_duration_indiv =  cumulativeTime - servers[next_s].cOut;
            q_duration_indiv =  servers[next_s].cOut - cumulative_time;
            if (q_duration_indiv < 0)
                q_duration_indiv = 0;
            
            
            else //do we need "else" here?
                q_duration_total += q_duration_indiv;
            
            servers[next_s].cIn = max (cumulative_time , servers[next_s].cOut);
            servers[next_s].cOut = servers[next_s].cIn + customers[i].duration_ordering;
            
            servers[next_s].cID = customers[i].id;
            customers[i].duration_queuing = q_duration_indiv;
            customers[i].time_order_complete = servers[next_s].cOut;

            /// q size for each customer:
            int q_size = 0;
            for(int j = 0; j < i; j++){
                if (customers[j].time_order_complete > cumulative_time)
                    q_size++;
            }//end of for, j
    /*
    * Excel simulation uses the opposite comparison for measuring queue. Equal
    * values of "order_compete_time" and "cumulativeTime" means next customer  
    * comes in at the same moment when the previous one walks out. In Excel, 
    * it adds to queue size due to the way of comparison. Here it does not, due 
    * to a different compaison, which better models a real situation.
    */
            customers[i].q_size = q_size;
            q_size_total += q_size;
            q_size_max = max(q_size_max, q_size);
            
            if (q_duration_max < q_duration_indiv) 
                q_duration_max = q_duration_indiv;           
            //q_max = max(q_max,q_indiv);
            
            //Report:
                System.out.print("time: " + cumulative_time + "\t");        
                System.out.print("customer: " + customers[i].id + "\t");
                System.out.print("duration: " + customers[i].duration_ordering + "\t");
                System.out.print("next_s: " + next_s + "\t");
                System.out.print("q_indiv: " + q_duration_indiv + "\t");
                System.out.print("q_total: " + q_duration_total + "\t");
                System.out.print("c_in:" + servers[next_s].cIn + "\t");
                System.out.print("c_out:" + servers[next_s].cOut + "\t");
                System.out.print("q_size:" + customers[i].q_size + "\t");
                System.out.print("\n");
        }//enf of for, i
        System.out.println("Total queuing time: " + q_duration_total);
        System.out.println("Average queuing time: " + q_duration_total / (double)n);
        System.out.println("Maximum queuing time: " + q_duration_max);
        System.out.println("Maxumin queue size: " + q_size_max);
        System.out.println("Average queue size: " + q_size_total / (double)n);

        
    }//end of main
    
    public static int max(int t, int v){return t < v ? v : t;}
    public static int min(int t, int v){return t > v ? v : t;}
    
    public static void assign(Customer c, Server s, int cumulativeTime){
        cumulativeTime += c.duration_before;
        s.cID = c.id;
        s.cIn = max(cumulativeTime, s.cOut);
        s.cOut = cumulativeTime + c.duration_ordering;
    }//end of assign
    
    public static int checkAvailableServers(Server [] servers){
        int earliestTimeOff = servers[0].cOut;
        int s = 0;
        for(int i = 1; i < servers.length; i++){
            if (earliestTimeOff > servers[i].cOut){
                earliestTimeOff = servers[i].cOut;
                s = i;
            }
        } //end of for, i
        return s;
    }//end of checkAvailableServers
        
        
    public static Customer [] makeArrayOfCustomers(int n, int a, int b, int c, int d, int show){
        Customer [] res = new Customer [n];
        for(int i = 0; i < n; i++){
            res[i] = new Customer();
            res[i].id = i;
            res[i].duration_before = (int)(Math.random() * a + b);//rand norm
            res[i].duration_ordering = (int)(Math.random() * c + d);//rand norm
            if(show > 0)
                System.out.println(res[i].id + "\t" + res[i].duration_before + "\t" + res[i].duration_ordering);
            
            
            // for normal distribution use: 
            // nextGaussian() * sigma + mu
        }//end of for
        if(show > 0)
            System.out.println("--------------------");
        
        return res;
    }//end of makeArrayOfCustomers
    
    
    
}//end of class CoffeeShopSimulation
