package cn.edu.hqu.cst.kubang.exhibition.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: 邢佳成
 * @Date: 2020.02.18 00:05
 * @Description: 分页
 */
@Data
@NoArgsConstructor
public class Page<T> {
    //已知数据
    private int pageNum; //当前页，前端传过来
    private int pageSize; //每页显示的数量
    private int totalRecord; //总记录数

    //计算得来
    private int totalPage;//总页数 totalRecord➗pageSize + 1
    private int startIndex;//开始索引，即从第几个开始拿 limit startIndex,pageSize
    //分页显示的数据，比如在页面显示1，2，3，4，5页，start = 1，end = 5 这也是计算得来
    private int start;
    private int end;

    private List<T> list; //将每页数据放进List中

    //默认显示5页
    public Page(int pageNum, int pageSize, int totalRecord) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;

        this.totalPage = totalRecord % pageSize == 0 ? totalRecord % pageSize : totalRecord % pageSize + 1;

        this.startIndex = (pageNum - 1) * pageSize;

        this.start = 1;
        this.end = 5;

        if (totalPage <= 5)
            this.end = this.totalPage; //总共页数小于5页
        else {
            //总共页大于5页，需要计算开始页数与结束页数
            this.start = pageNum - 2;
            this.end = pageNum + 2;

            if (start < 0) {
                this.start = 1;
                this.end = 5;
            }
            if (end > this.totalPage) {
                this.end = totalPage;
                this.start = end - 5;
            }
        }
    }
}
