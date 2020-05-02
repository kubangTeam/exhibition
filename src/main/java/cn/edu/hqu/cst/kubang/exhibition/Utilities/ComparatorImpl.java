package cn.edu.hqu.cst.kubang.exhibition.Utilities;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;

import java.util.Comparator;

public class ComparatorImpl implements Comparator<Exhibition> {
    @Override
    public int compare(Exhibition o1, Exhibition o2) {
        if(o1.getStartTime().compareTo(o2.getStartTime())==1)
            return 1;
        else if(o1.getStartTime().compareTo(o2.getStartTime())==-1)
            return -1;
        else
            return 0;
    }
}
