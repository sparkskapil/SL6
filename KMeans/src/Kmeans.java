import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Kmeans {
	private int K;						//Number of Clusters
	private int centroids[];			//Centroids
	private List<Integer> Clusters[];	//Clusters
	private int DataSet[];				//Data Set

	//Constructor 
	public Kmeans(int K,int N) {
		this.K = K;
		centroids = new int[K];
		Clusters = new List[K];
		DataSet = new int[N];
	}
	
	//Retrieve DataSet From User
	public void SetDataset() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Elements: ");
		for(int i=0;i<DataSet.length;i++) {
			DataSet[i] = scanner.nextInt();
		}
	}
	
	//Print DataSet
	public void GetDataSet() {
		System.out.println("The DataSet is -");
		for(int i : DataSet) {
			System.out.print(i + " ");
		}
		System.out.println("");
	}
	
	public boolean Same(List<Integer> tempClusters[]) {
		for(int i=0;i<tempClusters.length;i++) {
			if(tempClusters[i].size()!=Clusters[i].size())
				return false;
			for(int j=0;j<tempClusters[i].size();j++) {
				if(tempClusters[i].get(j)!=Clusters[i].get(j)) 
					return false;
			}
		}
		return true;
	}
	
	//Start Clustering
	public void Start() {
		int pass = 1;
		for(int i=0;i<this.K;i++) {
			Clusters[i] = new ArrayList();
		}
		initCentroids();
		
		List<Integer>[] tempClusters = new List[this.K];
		do {
			System.out.println("Clustering in Pass " + pass++ +"\n");
		    for(int i=0;i<this.K;i++) {
				tempClusters[i] = new ArrayList(Clusters[i]);
		    }
			
		    Cluster();
			
		    System.out.println("Clusters");
		    getClusters();
			UpdateCentroids();
		}while(!Same(tempClusters));
		
		System.out.println("Clustering Complete!!!");
		System.out.println("Final Clusters are - ");
		getClusters();
		
	}
	
	//Print Clusters
	public void getClusters() {
		for(List<Integer> Cluster : Clusters) {
			for(int i=0;i<Cluster.size();i++) {
				System.out.print(Cluster.get(i)+" ");
			}
			System.out.println("");
		}
	}
	
	//Assign data into Clusters 
	public void Cluster() {
		
		int dist[] = new int[this.K];

		for(int i=0; i<this.K; i++) {
			Clusters[i].clear();
		}
		
		for(int data : DataSet) {
			for(int i=0;i < centroids.length; i++)
				dist[i] = Math.abs(data - centroids[i]);
			Clusters[getCluster(dist)].add(data);
		}
	}
	
	//On Basis of Min Distance Get Index Of Cluster
	//in which data is to be assigned
	public int getCluster(int Distance[]) {
		int ind = 0;
		int min = Distance[0];
		for(int i=1;i<Distance.length;i++) {
			if(min>Distance[i]) {
				min = Distance[i];
				ind = i;
			}
		}
		return ind;
	}
	
	
	//Set Random Centroids For First Pass
	public void initCentroids() {
	
		//Check For Duplicate Data and Reduce No Of Clusters Accordingly
		HashMap<Integer,Integer> Data = new HashMap();
		for(int data: DataSet) {
			Data.put(data, 1);
		}
		
		//If DataSet is Too Small to be Clustered in K Clusters
		if(this.K>Data.size()) {
			System.out.println("DataSet Too Less. Resetting Number of Clusters...");
			K=Data.size();
			Iterator<Integer> itr = Data.keySet().iterator();
			int l=0;
			while(itr.hasNext()) {
				centroids[l++]=itr.next();
			}
		}
		
		//Else Generate Random Centroids
		for(int i=0;i<this.K;i++) {
			int c = (int) (Math.random()*DataSet.length);
			for(int centroid:centroids) {
				if(centroid == DataSet[c]) {
					i--;
					break;
				}
			}
			centroids[i] = DataSet[c];
		}
	}
	

	//The Mean Function
	public void UpdateCentroids() {
		int k=0;
		for(List<Integer> Cluster : Clusters) {
			int Sum=0;
			int mean=0;
			for(int i=0;i<Cluster.size();i++) {
				Sum += Cluster.get(i);
			}
			if(Cluster.size()!=0)//Avoid Divide By Zero Exception
				mean = (int)(Sum/Cluster.size());
			centroids[k++]=mean;
		}
	}
	
	
	public void getCentroids() {
		System.out.println("The Centroids Are-");
		for(int centroid: centroids) {
			System.out.print(centroid + " ");
		}
		System.out.println("");
	}
	
	
	
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		
		//Receive Numbers of Clusters
		System.out.println("Enter Number of Clusters:");
		int noClusters = scanner.nextInt();

		System.out.println("Enter Number of Elements:");
		int noElements = scanner.nextInt();
		
		Kmeans Clustering = new Kmeans(noClusters,noElements);
		
		//Receive Inputs

		System.out.println("Initialization Phase Started");
		Clustering.SetDataset();
		Clustering.GetDataSet();
		Clustering.initCentroids();
		Clustering.getCentroids();
		System.out.println("Initialization Phase Ends");
		
		System.out.println("Clustering Phase Started");
		Clustering.Start();
		System.out.println("Clustering Phase Ends");
	}
}
