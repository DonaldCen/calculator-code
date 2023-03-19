package cn.com.dc;

/**
 * @Description
 * @Author yingqiang.Cen
 * @Date 2023/3/20
 * @Version 1.0.0
 */
public interface Command {

    void execute();

    void undo();
}
