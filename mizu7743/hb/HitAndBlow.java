package mizu7743.hb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HitAndBlow {
	public static final int NUM =4;
	private int [] answer;
	private int [] input;
	private int hit;
	private int blow;
	
	HitAndBlow(){
		answer =makeAnswer();
		hit = 0;
		blow = 0;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HitAndBlow hb = new HitAndBlow();
		Cpu hbCpu = new Cpu();
		List<Cpu> list = hbCpu.makeCandidate();
		
//		HB cpuhb = new HB();
		while(true){
			hb.makeInput();
			hbCpu.check(list, hb);
			
			//hb.show();
			if(hb.check()){
				hb.result();
				break;
			}
			if(list.size()==1){
				hbCpu.clear(list);
			}
			hb.result();
		}
	}
	/**
	 * HitAndBlowの答えを作成するメソッド
	 * @return 答えとなる数字の配列(とりあえず4桁）
	 */
	
	public int[] makeAnswer(){
		
		int numList[]= {0,1,2,3,4,5,6,7,8,9};
		int answer[] = new int[NUM];
		Random rand = new Random();
		int index;
		//最上位桁には0がこないように
		index = rand.nextInt(numList.length-2)+1;
		answer[0] = numList[index];
		//一番後ろの数を今使った数の位置に上書き
		numList[index] = numList[numList.length-1];
		
		for(int i = 1; i < answer.length; i++){
			index = rand.nextInt(numList.length-(i+1));
			answer[i] = numList[index];
			numList[index] = numList[numList.length-(i+1)];
		}
		return answer;
	}
	
	/**
	 * 作成した答えを表示するメソッド
	 */
	
	public void show(){
		System.out.print("answer:");
		for(int i : answer){
			System.out.print(i);
		}
		System.out.println();
	}
	
	/**
	 * 入力を受け付ける、正規表現でチェック
	 */
	public void makeInput(){
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf = new BufferedReader(isr);
		String str = null;
		Pattern p = Pattern.compile("[1-9][0-9]{"+ (NUM-1) +"}");
		Matcher m;
		boolean check;
		
		while(true){
			System.out.println("重複のない"+NUM+"桁の整数を入力してください（はじめは０以外）");
			try{
				str = bf.readLine();
				
			}
			catch(IOException ie){
				ie.printStackTrace();
			}
			
			m = p.matcher(str);
			if(m.matches()){
				//重複チェック、正規表現で文字数は確定
				check = true;
				for(int i = 0;i < str.length()-1;i++){
					for(int j =i+1;j < str.length();j++){
						if(str.charAt(i) == str.charAt(j)){
							check = false;
						}
					}	
				}
				if(check){
					break;
				}
			}
			System.out.println("入力が正しくないためもう一度入力してください");
		}
		input = new int[NUM];
		for(int i =0; i < input.length; i++){
			input[i] = str.charAt(i)-'0';
		}
	
		
	}
	/**
	 * Hit Blowのチェック 終了判定も微妙に行う
	 * @return　true すべてhitの場合　false　それ以外
	 */
	public boolean check(){
		hit = 0;
		blow = 0;
		for(int i = 0; i < input.length; i++){
			for(int j = 0; j < answer.length; j++){
				if(input[i] == answer[j]){
					if (i == j){
						hit++;
					}
					else{
						blow++;
					}
				}
			}
		}
		if(hit == NUM){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * hit blowの数の表示
	 *
	 */
	public void result(){
		System.out.print("input:");
		for(int i : input){
			System.out.print(i);
		}
		System.out.println(" hit:" + hit +" blow:"+blow);
		if(hit == NUM){
			System.out.println("正解です!");
		}
	}
	
	public int[] getAnswer(){
		return answer;
		
	}
	/**
	 * hit* 10 + blow の値を返す
	 */
	public int getHitBlow(int[] input, int [] answer){
		int hit = 0;
		int blow = 0;
		for(int i = 0; i < input.length; i++){
			for(int j = 0; j < answer.length; j++){
				if(input[i] == answer[j]){
					if (i == j){
						hit++;
					}
					else{
						blow++;
					}
				}
			}
		}
		return hit * 10 + blow;
	}
}

