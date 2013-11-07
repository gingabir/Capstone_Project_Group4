/**
 * 
 */
package Default;

import java.io.*;
import java.util.*;
import java.net.*;

/**
 *authors: GROUP4 - CAPSTONE PROJECT
 *Gloria INGABIRE
 *Lambert SENDEGEYA
 *Emmanuel NGIRINSHUTI
 *
 */
public class SearchDocument {
	@SuppressWarnings("rawtypes")
	public static Hashtable<String, Vector> wordsIndex = new Hashtable<String, Vector>();	

	public SearchDocument()
	{
		new Thread();		
		LoadFromCache();
		buildCache();
	}

	public boolean csvExists(){
		boolean isPresent = false;
		File dictionaryFile = new File("dictionaryCache.csv");
		if(dictionaryFile.exists()){
			isPresent = true;
		}
		return isPresent;
	}

	public boolean cacheExists(){
		boolean isPresent = false;
		File documentFile = new File("documentCache.txt");
		if(documentFile.exists()){
			isPresent = true;
		}
		return isPresent;
	}

	@SuppressWarnings("unchecked")
	public boolean LoadFromCache(){
		boolean success = false;
		try{
			BufferedReader br = new BufferedReader(new FileReader("dictionaryCache.csv"));
			String line = br.readLine();
			while(line != null || line != ""){
				Vector<?> links = new Vector<Object>();
				if(line == null){
					break;
				}
				String[] lineParts = line.split(",");
				wordsIndex.put(lineParts[0], links);
				for(int i=1; i<lineParts.length; i++){
					(wordsIndex.get(lineParts[0])).addElement(lineParts[i]);
				}
				line = br.readLine();
			}
			br.close();
			success = true;
		}
		catch(Exception e){
			success = false;
		}
		return success;
	}

	@SuppressWarnings("unchecked")
	public void ReadDoc(String page, URL url){
		page = page.replaceAll("[^A-Za-z]", " ");
		String[] words = page.split(" ");
		for(int i=0; i<words.length; i++){
			String word = (words[i].trim()).toLowerCase();
			Vector<?> links = new Vector<Object>();
			//if(!word)
			if(!word.isEmpty() && !wordsIndex.containsKey(word)){
				wordsIndex.put(word, links);
				wordsIndex.get(word).addElement(url);
			}
			else{
				if(!word.isEmpty() && !wordsIndex.get(word).contains(url)){
					wordsIndex.get(word).addElement(url);
				}
			}
		}
	}

	public boolean saveToCsv(){
		boolean success = false;
		Vector<?> links = new Vector<Object>();
		try{
			File dictionaryFile = new File("dictionaryCache.csv");
			if(!dictionaryFile.exists()){
				dictionaryFile.createNewFile();
			}
			FileWriter fwDictionary = new FileWriter((dictionaryFile.getAbsoluteFile()), true);
			BufferedWriter bwDictionary = new BufferedWriter(fwDictionary);
			for(String key: wordsIndex.keySet()){
				//System.out.println(key);
				bwDictionary.write(key+",");
				links = wordsIndex.get(key);
				for(int i=0; i<links.size()-1; i++){
					bwDictionary.write(links.get(i).toString()+",");
				}
				bwDictionary.write(links.get(links.size()-1).toString()+"\n");
			}
			bwDictionary.close();
			success = true;
		}
		catch(Exception e){
		}
		return success;
	}

	public static HashSet<String> getIntersection(HashSet<String> set1, HashSet<String> set2) {
		set1.retainAll(set2);
		return set1;
	}
	public String find(String keyword){
		String results = "";
		keyword = keyword.toLowerCase();
		String[] keywords = keyword.split(" ");
		HashSet<String> set1 = new HashSet<String>();
		HashSet<String> set2 = new HashSet<String>();
		for(int i=0; i<keywords.length; i++){
			if(wordsIndex.containsKey(keywords[i])){
				set2 = new HashSet<String>();
				Vector<?> links = new Vector<Object>();
				links = wordsIndex.get(keywords[i]);

				for(int j=0; j<links.size(); j++){
					if(i==0){
						set1.add(links.get(j).toString());
					}
					else{
						set2.add(links.get(j).toString());
					}
				}
			}
			if(i!=0 && set1.size()>0 && set2.size()>0){
				set1 = getIntersection(set1,set2);
			}
		}
		for(String link: set1){
			results = results+link+"\n";
		}
		if(results.equals("")){
			results = "No match found for "+keyword+"\n";
		}
		return results;
	}
	public void buildCache()
	{
		System.out.println("Thread starting.....");
		Thread thread = new Thread(new Runnable() { 
			public void run() {
				CrawlerEngine c=new CrawlerEngine();
				c.Crawl();
			}
		}
				);
		thread.start();

	}

}
