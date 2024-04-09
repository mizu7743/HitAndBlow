package mizu7743.hb;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
public class Cpu {
	int [] candidate;
	
	/**
	 * 解答候補を作成する
	 */
	public List<Cpu> makeCandidate(){
		List<Cpu> array = new ArrayList<Cpu>();
		
		int num = (int)Math.pow(10, HitAndBlow.NUM-1);
		//sSystem.out.println(num);
		//System.out.println(Math.pow(10,0));
		label:
		for(int i =num;i < num*10; i++){
		//	System.out.println("i"+ i);
			Cpu hbcpu = new Cpu();
			hbcpu.candidate =new int[HitAndBlow.NUM];
			for(int j = 0 ,temp = i; j < HitAndBlow.NUM; j++){
			//	System.out.println(temp / (num /(int)Math.pow(10, j)));
				hbcpu.candidate[j] = temp / (num /(int)Math.pow(10, j));
				temp = temp % (num /(int)Math.pow(10, j));
			//	System.out.println(temp);
			}
			for(int k = 0; k < HitAndBlow.NUM-1; k++){
				for(int l = k+1; l < HitAndBlow.NUM; l++){
					if (hbcpu.candidate[k] == hbcpu.candidate[l]){
						//System.out.println("conti:l:"+ l+"k:"+k+"can[l]"+hbcpu.candidate[l]+"can[k]"+hbcpu.candidate[k]);
						continue label;
					}
				}
			}
			array.add(hbcpu);
		//	System.out.println("added");
		}
		System.out.println(array.size()+"個の正解候補を作成しました");
		return array;
	}
	
	/**
	 * 解答候補を表示
	 */
	public void show(List<Cpu> list){
		for(Cpu hbcpu : list){
			System.out.print("candidate:");
			for(int i : hbcpu.candidate){
				System.out.print(i);
			}
			System.out.println();
		}
		
	}
//	public static void main(String[] args){
//		HBCpu hbcpu = new HBCpu();
//		List<HBCpu> list = hbcpu.makeCandidate();
//		hbcpu.show(list);
//	}
	/**
	 * 正解候補を選ぶ privateでもよさそう
	 * @param list
	 */
	public Cpu select(List<Cpu> list){
		Random rand = new Random();
		Cpu selectedHBCpu = list.get(rand.nextInt(list.size()));
		System.out.print("CPUが選んだ数は：");
		for(int i : selectedHBCpu.candidate){
			System.out.print(i);
		}
		System.out.println(" です");
		return selectedHBCpu;
	}
	
	/**
	 * リストから正解にならない候補を削る
	 */
	public void check(List<Cpu> list,HitAndBlow hb){
		int [] candidate = select(list).candidate;
		int hbnum = hb.getHitBlow(candidate,hb.getAnswer());
	//	System.out.println("hbnum:" + hbnum);
		int hbnumTemp;
		for(Iterator<Cpu> iter = list.iterator();iter.hasNext();){
			Cpu hbcpu = iter.next();
			hbnumTemp = hb.getHitBlow(candidate,hbcpu.candidate);
		//	System.out.println("hnumTemp:" + hbnumTemp);
			if(hbnum != hbnumTemp){
				iter.remove();
			}
		}
		System.out.println("正解候補数が"+list.size()+"個になりました");
	}
	
	public void clear(List<Cpu> list){
		for(Cpu hbcpu : list){
			System.out.print("CPUが正解:");
			for(int i : hbcpu.candidate){
				System.out.print(i);
			}
			System.out.println("を当てました");
		}
		
	}
}
