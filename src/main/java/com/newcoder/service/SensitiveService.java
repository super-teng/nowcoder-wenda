package com.newcoder.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Super腾 on 2017/2/12.
 */
@Service
public class SensitiveService implements InitializingBean{

    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    /**
     * 可以读取配置文件信息
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream is = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try{
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            inputStreamReader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = new String();
            while((line = bufferedReader.readLine())!=null){
                addWord(line);
            }
        }catch (Exception e){
            logger.error("读取配置文件错误"+e.getMessage());
        }finally {
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(inputStreamReader != null){
                inputStreamReader.close();
            }
            if(is != null){
                is.close();
            }
        }

    }

    /**
     * 字典树节点，包含两个属性，当前是否是敏感词结尾，以及下一层的集合信息
     * 这两个属性的设值和取值的方法
     */
    private class TreeNode{
        //判断当前是否是敏感词的结尾
        private boolean end = false;

        //当前节点的下一层节点
        private Map<Character,TreeNode> subNodes = new HashMap<>();

        //添加节点信息key是当前节点的信息，treenode是所含节点的包装类
        public void setSubNode(Character key, TreeNode treeNode){
            subNodes.put(key,treeNode);
        }

        //获取当前节点下的一个子节点
        public TreeNode getSubNode(Character key){
            return subNodes.get(key);
        }

        //判断当前节点是否是结尾节点
        public boolean isKeyWorldEnd(){
            return end;
        }

        //设置当前节点是否是结尾节点
        public void setKeyWorldEnd(Boolean end){
            this.end = end;
        }
    }

    //当前字典树的根节点
    private TreeNode root = new TreeNode();

    /**
     * 添加需要过滤的关键字
     * @param lineText
     */
    private void addWord(String lineText){
       TreeNode treeNode = root;
       for(int i=0;i<lineText.length();i++){
           Character c = lineText.charAt(i);
           //如果是特殊字符构建树的时候自动把它去除
           if(isSymbol(c)){
               continue;
           }
           TreeNode node = treeNode.getSubNode(c);
           if(node == null){
               node = new TreeNode();
               treeNode.setSubNode(c,node);
           }
           treeNode = node;
           if(i == lineText.length()-1){
               treeNode.setKeyWorldEnd(true);
           }
       }
    }

    /**
     * 过滤敏感词
     * @param text
     * @return
     */
    public String filter(String text){
        if(StringUtils.isEmpty(text)){
            return text;
        }
        StringBuilder result = new StringBuilder();
        String replacement = "***";
        //指向字典树的指针
        TreeNode tempNode = root;
        //敏感词开头索引
        int begin = 0;
        //当前词扫描的索引
        int position = 0;
        while(position < text.length()){
            char c = text.charAt(position);
            //如果是非法词不影响判断
            if(isSymbol(c)){
                //当这个特殊词开头的时候直接跳到下一位去
                if(tempNode == root){
                    result.append(c);
                    begin++;
                }
                position++;
                continue;
            }
            //看字典树当前层中是否有这个节点
            tempNode = tempNode.getSubNode(c);
            //如果当前没有这个字符，把它加入结果中，并把position和begin往后移到begin后一位，tempNode还是指向root
            if(tempNode == null){
                result.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = root;
            }else if(tempNode.isKeyWorldEnd()){
                //发现敏感词
                result.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = root;
            }else{
                //发现敏感字但不确定是敏感词时继续向后扫描
                ++position;
            }
        }

        //如果是以部分敏感词结尾的话将其添加到结果中去
        result.append(text.substring(begin));

        return result.toString();
    }

    //判断是否有非法字符
    private boolean isSymbol(char c){
        int ic = (int)c;
        //既不是东亚文字也不是英文字符就返回true
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

//    public static void main(String[] args){
//        SensitiveService sensitiveService = new SensitiveService();
//        sensitiveService.addWord("胖胖");
//        System.out.println(sensitiveService.filter("你是一个胖&&胖么"));
//    }

}
