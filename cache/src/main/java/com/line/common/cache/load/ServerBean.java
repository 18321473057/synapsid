package com.line.common.cache.load;

//服务对象
public class ServerBean implements Comparable<ServerBean> {
    private String name;
    //权重
    private Integer weight;

    public ServerBean(String name, Integer weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        return "ServerBean{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }

    //自定义排序
    @Override
    public int compareTo(ServerBean o) {
        if (this.getWeight().equals(o.getWeight())) {
            return 0;
        }
        return this.getWeight() > o.getWeight() ? -1 : 1;
    }
}
