//icsd13190 Tsoulos Sotiris katanemhmena2
package boardserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//Import tis klaseis apo ton client 
import boardclient.Message;
import boardclient.Anakoinwsh;
import boardclient.Eggrafh;

class BoardServer {
    
    
    
    
    
    public static void main(String[] args) throws ClassNotFoundException {
        
        try {
            int UniqueId=1;
            //Dunamikh domh dedomenwn pou apothikevw tis kataxwrhseis kai eggrafes moy
            ArrayList <Anakoinwsh> anakoinwseis = new ArrayList();
            ArrayList <Anakoinwsh> SearchResults = new ArrayList();
            ArrayList <Eggrafh> subscription = new ArrayList();
            
            //Dhmiourgw to server socket
            ServerSocket ServerSock = new ServerSocket(5555);           
            
            
            //Kai "anoigw ton server"
            while(true){
                //Perimenw to connection
                Socket sock = ServerSock.accept();
   
                //Kathws kai objectstreams gia antalagh antikeimenwn
                ObjectOutputStream os = new ObjectOutputStream(sock.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(sock.getInputStream());
                
                //Dhmiourgw antikeimeno Message 
                         
                Message message = (Message) is.readObject();               
                Anakoinwsh notice = new Anakoinwsh();
                Eggrafh sub = new Eggrafh();
                
                
                //An o Client mou exei steilei hello mpainw sthn if gia na arxisei h diadikasia
                if(message.getMessage().equalsIgnoreCase("START")){
                    //Allazw to munhma se WAITING kai to stelnw ston client
                    message.setMessage("WAITING");
                    os.writeObject(message);
                    os.flush();
                    
                    //Diavazw ton tupo aithmatos pou mou stelnei o Client 
                    message = (Message) is.readObject();                   
                    //Periptwsh gia INSERT
                    if(message.getMessage().equalsIgnoreCase("INSERT")){
                        notice = (Anakoinwsh) is.readObject();
                        //kalw methodo gia Id                         
                        notice.setId(UniqueId);
                        //kai to auksanw gia to epomeno entry wste na einai monadiko
                        UniqueId++;
                        
                        //Kai vazw thn anakoinwsh pou egine sto arraylist wste na th diaxeiristw argotera se periptwsh allhs entolhs apo client
                        anakoinwseis.add(notice);
                        
                        //Sth sunexeia stelnw to ok  oti h diadikasia termatise
                        message.setMessage("OK");
                        os.writeObject(message);
                        os.flush();
                        
                        //Epistrefw to ananewmeno antikeimeno
                        os.writeObject(notice);
                        os.flush();
                                           
                    }
                    //Periptwsh gia SUBSCRIBE
                    else if(message.getMessage().equalsIgnoreCase("SUBSCRIBE")){
                        sub = (Eggrafh) is.readObject();
                        
                                                
                        
                        
                        //Kai vazw ta username kai password pou ginane sto arraylist
                        subscription.add(sub);
                        
                        
                        //Sth sunexeia stelnw to ok  oti h diadikasia termatise
                        message.setMessage("OK");
                        os.writeObject(message);
                        os.flush();
                        
                        //Epistrefw to ananewmeno antikeimeno
                        os.writeObject(sub);
                        os.flush();
                                           
                    }
                    //Periptwsh gia SEARCH
                    else if(message.getMessage().equalsIgnoreCase("SEARCH")){
                        //Diavazw to antikeimen opou mou esteile  xrhsths gia na vrw an yparxei
                        notice = (Anakoinwsh) is.readObject();
                        //anatrexw to arraylist me tis kategramenes anakoinwseis
                        for(int i=0;i<anakoinwseis.size();i++){
                            //kai elegxw an yparxei 
                            if((notice.getID()==anakoinwseis.get(i).getID() && notice.getName().equalsIgnoreCase(anakoinwseis.get(i).getName()) && notice.getSurname().equalsIgnoreCase(anakoinwseis.get(i).getSurname()) && notice.getTitlos().equalsIgnoreCase(anakoinwseis.get(i).getTitlos()) && notice.getTitlos().equalsIgnoreCase(anakoinwseis.get(i).getText())  ) || ( notice.getID()==anakoinwseis.get(i).getID() && notice.getName().equalsIgnoreCase(anakoinwseis.get(i).getName()) && notice.getSurname().equalsIgnoreCase(anakoinwseis.get(i).getSurname()) && notice.getSurname().equalsIgnoreCase(anakoinwseis.get(i).getSurname()) && notice.getTitlos().equalsIgnoreCase(anakoinwseis.get(i).getTitlos()) && notice.getTitlos().equalsIgnoreCase(anakoinwseis.get(i).getText()) )){
                                //Vazei sto arraylist Searchreults oles tis anakoinwseis pou sumfwnoun me auta pou anazhthse o xrhsths
                                SearchResults.add(anakoinwseis.get(i));                             
                            }                           
                        }
                        //kai epistrefw to arraylist me ta searchresults
                        os.writeObject(SearchResults);
                        os.flush();
                        
                        //kai stelnw ok
                        message.setMessage("OK");
                        os.writeObject(message);
                        os.flush();
     
                    }
                    //Periptwsh gia delete
                    else if(message.getMessage().equalsIgnoreCase("DELETE")){
                        //Diavazw to antikeimen opou mou esteile  xrhsths gia na vrw an yparxei
                        notice = (Anakoinwsh) is.readObject();
                        //Kai psaxnw tis anakoinwseis
                        for(int i=0;i<anakoinwseis.size();i++){
                            //Ama to id einai idio diegrapse thn eggrafh
                            if(notice.getID()==anakoinwseis.get(i).getID()){
                                //Kai diagrafei to antikeimeno
                                anakoinwseis.remove(i);
                                message.setMessage("DELETE OK");
                                os.writeObject(message);
                                os.flush();                               
                            }
                            //Ama den vrei antikeimeno me antistoixo id
                            else{
                                message.setMessage("DELETE FAIL");
                                os.writeObject(message);
                                os.flush();
                                os.close();
                            }
                        }
                        message.setMessage("OK");
                        os.writeObject(message);
                        os.flush();
                    }
                }
                //Alliws diekopse th sundesh ama den pareis start
                else{
                    System.out.println("Den phre Start Server");
                    os.close();
                    is.close();   
                    sock.close();
                }

            }
            
        } catch (IOException ex) {
            Logger.getLogger(BoardServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
