package com.FourIL.AlarmApp;

import java.util.LinkedList;

import android.util.Log;

public class InfoPackList {
	private static final int MAX_NUM_INFOPACK = 100;
	private static InfoPackList singleInstance = null;

	//Singleton
	public static synchronized InfoPackList getInstance() {
		  if (singleInstance == null)
		   singleInstance = new InfoPackList();
		  return singleInstance;
	}

	private InfoPackList() {
		list = new LinkedList<InfoPack>();
		InfoPack pack = new InfoPack();
		pack.music = "";
	}

	private LinkedList<InfoPack> list;

	void addInfoPack(InfoPack p) {
		Log.i("pyf", p.music);
		list.add(p);
		if (list.size() > MAX_NUM_INFOPACK)
			list.remove(0);
	}

	public InfoPack[] getInfoPacks(int num) {
		if (num > list.size())
			num = list.size();
		InfoPack[] infos = new InfoPack[num];
	    for (int i = 0; i < num; i++)
	    	infos[i] = list.get(i);
		return infos;
	}

	public InfoPack getLatest() {
		return list.getLast();
	}

}