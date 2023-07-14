package com.line.common.cache.load;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @Author: yangcs
 * @Date: 2021/3/13 9:59
 * @Description:
 */
public class WeightRule {
    private static List<ServerBean> serverBeans = new ArrayList<ServerBean>() {{
        add(new ServerBean("name1" , 10));
        add(new ServerBean("name3" , 70));
        add(new ServerBean("name2" , 20));
    }};


    public static void main(String[] a) {
        ServerBeanWeightAware serverBeanWeightAware = new ServerBeanWeightAware(serverBeans);
        ServerBean bean = getSelectBean(serverBeanWeightAware);
        System.out.println(bean);
    }

    private static ServerBean getSelectBean(ServerBeanWeightAware serverBeanWeightAware) {
        //获取概率分布
        List<Integer> weightDistribute = serverBeanWeightAware.getWeightList();
        int random = new Random().nextInt(weightDistribute.get(weightDistribute.size() - 1));
        for (int i = 0; i < weightDistribute.size(); i++) {
            if (random <= weightDistribute.get(i)) {
                //选中的服务对象
                return serverBeanWeightAware.getSortServerBeans().get(i);
            }
        }
        return null;
    }

    private static void soutList(List<? extends Object> serverBeans) {
        serverBeans.forEach(bean -> {
            System.out.println(bean);
        });
    }

    static class ServerBeanWeightAware {
        //权重分布图
        private List<Integer> weightList;
        //排序后的服务对象列表
        private List<ServerBean> sortServerBeans;

        public ServerBeanWeightAware(List<ServerBean> originserverBeans) {
            weightList = new ArrayList<>(originserverBeans.size());
            sortServerBeans = new ArrayList<>(originserverBeans.size());
            buildWeightList(originserverBeans);

            soutList(sortServerBeans);
            soutList(weightList);

        }

        //构建排序数据
        void buildWeightList(List<ServerBean> serverBeans) {
            //排序
            Collections.sort(serverBeans);
            sortServerBeans = serverBeans;
            //绘制权重分布图;
            int weightSum = 0;
            for (ServerBean bean : sortServerBeans) {
                weightSum += bean.getWeight();
                weightList.add(weightSum);
            }
        }


        public List<Integer> getWeightList() {
            return weightList;
        }

        public void setWeightList(List<Integer> weightList) {
            this.weightList = weightList;
        }

        public List<ServerBean> getSortServerBeans() {
            return sortServerBeans;
        }

        public void setSortServerBeans(List<ServerBean> sortServerBeans) {
            this.sortServerBeans = sortServerBeans;
        }
    }


}
