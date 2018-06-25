package homework1.interfaces;

/**
 * 命令接口 新增命令只需实现本接口 重写执行方法，传入命令即可
 */
public interface Command {

    String execute(String command);

}
