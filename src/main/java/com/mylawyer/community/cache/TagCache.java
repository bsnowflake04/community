package com.mylawyer.community.cache;

import com.mylawyer.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author bsnowflake04
 * Date on 2020/4/6  19:48
 */
public class TagCache {
    public static List<TagDTO> get() {
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO category0 = new TagDTO();
        category0.setCategoryName("常见问题");
        category0.setTags(Arrays.asList("婚姻家庭", "交通事故", "刑事辩护", "合同纠纷", "公司法", "拆迁安置", "劳动纠纷", "债权债务",
                "医疗纠纷", "房产纠纷", "知识产权", "侵权"));
        tagDTOS.add(category0);

        TagDTO category1 = new TagDTO();
        category1.setCategoryName("宪法及宪法相关法");
        category1.setTags(Arrays.asList("宪法及宪法相关法", "宪法", "国旗法", "国徽法", "国籍法", "戒严法", "立法法", "缔结条约程序法",
                "专属经济区和大陆架法", "集会游行示威法", "领海及毗连区法", "引渡法", "民族区域自治法", "工会法", "外交特权与豁免条例",
                "领事特权与豁免条例"));
        tagDTOS.add(category1);

        TagDTO category2 = new TagDTO();
        category2.setCategoryName("民商法");
        category2.setTags(Arrays.asList("民商法", "民法通则", "婚姻法", "继承法", "物权法", "债权法", "人身权法", "知识产权法", "担保法",
                "合同法", "侵权责任法", "著作权法", "专利法", "商标法", "商法", "公司法", "合伙企业法", "个人独资企业法", "外商投资企业法",
                "中外合资经营企业法", "中外合作经营企业法", "外资企业法", "企业破产法", "保险法", "票据法", "海商法", "证券法"));
        tagDTOS.add(category2);

        TagDTO category3 = new TagDTO();
        category3.setCategoryName("行政法");
        category3.setTags(Arrays.asList("行政法", "一般行政法", "税收征收管理法", "行政复议法", "行政处罚法", "行政监察法", "公务员法",
                "特别行政法", "治安管理处罚法", "海关法", "教育法", "国家赔偿法", "劳动保障监察条例", "食品卫生法", "军事设施保护法",
                "土地管理法", "工伤认定法", "天气法"));
        tagDTOS.add(category3);

        TagDTO category4 = new TagDTO();
        category4.setCategoryName("刑法");
        category4.setTags(Arrays.asList("刑法", "刑法综合规定与解释", "犯罪和刑事责任", "刑罚", "量刑", "自首", "数罪并罚",
                "缓刑", "减刑", "假释", "危害国家安全罪", "危害公共安全罪", "破坏社会主义市场经济秩序罪", "侵犯公民人身权利、民主权利罪",
                "侵犯财产罪", "妨害社会管理秩序罪", "妨害婚姻家庭罪", "危害国防利益罪", "贪污贿赂罪", "渎职罪", "军人违反职责罪"));
        tagDTOS.add(category4);

        TagDTO category5 = new TagDTO();
        category5.setCategoryName("经济法");
        category5.setTags(Arrays.asList("经济法", "反不正当竞争法", "消费者权益保护法", "产品质量法", "广告法", "预算法", "审计法",
                "会计法", "银行法", "价格法", "税收征收管理法", "个人所得税法", "城市房地产管理法", "土地管理法", "土地承包法"));
        tagDTOS.add(category5);

        TagDTO category6 = new TagDTO();
        category6.setCategoryName("社会保障");
        category6.setTags(Arrays.asList("社会保障", "劳动法", "劳动合同法", "工会法", "未成年人保护法", "老年人权益保障法", "妇女权益保障法",
                "公务员法", "残疾人保障法", "矿山安全法", "红十字会法", "公益事业捐赠法"));
        tagDTOS.add(category6);

        TagDTO category7 = new TagDTO();
        category7.setCategoryName("诉讼及非诉讼程序");
        category7.setTags(Arrays.asList("诉讼及非诉讼程序", "刑事诉讼法", "民事诉讼法", "行政诉讼法", "海事诉讼特别程序法", "仲裁法"));
        tagDTOS.add(category7);

        TagDTO category8 = new TagDTO();
        category8.setCategoryName("环境保护");
        category8.setTags(Arrays.asList("环境保护法", "大气污染防治法", "水污染防治法", "海洋环境保护法", "固体废物污染环境防治法",
                "放射性污染防治法", "环境噪声污染防治法", "清洁生产促进法", "防沙治沙法", "环境影响评价法", "循环经济促进法"));
        tagDTOS.add(category8);
        return tagDTOS;
    }

    public static TagDTO getFive(){
        TagDTO tagDTO = new TagDTO();
        List<TagDTO> tagDTOS = TagCache.get();
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            arr.add(tagDTOS.get((int)(1+Math.random()*8)).getTags().get((new Random().nextInt(6)+1)));
        }
        tagDTO.setTags(arr);
        return tagDTO;
    }
    public static String filterInValid(String tags) {
        String[] split = StringUtils.split(tags, '；');
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tagDTO -> tagDTO.getTags().stream()).collect(Collectors.toList());
        String inValid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining("；"));
        return inValid;
    }
}
