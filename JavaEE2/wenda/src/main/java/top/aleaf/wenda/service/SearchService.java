/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SearchService
 * Author:   郭新晔
 * Date:     2019/2/1 0001 20:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.service;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;
import top.aleaf.wenda.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @create 2019/2/1 0001
 */
@Service
public class SearchService {
    private static final String SOLR_URL = "http://127.0.0.1:8983/solr/wenda";
    private HttpSolrClient solrClient = new HttpSolrClient.Builder(SOLR_URL).build();
    private static final String QUESTION_TITLE = "question_title";
    private static final String QUESTION_CONTENT = "question_content";

    public List<Question> searchQuestion(String keyword, int offset,
                                         int count, String hlPre, String hlPos)
            throws Exception {
        List<Question> questionList = new ArrayList<>();
        SolrQuery query = new SolrQuery(keyword);
        query.setRows(count);
        query.setStart(offset);
        query.setHighlight(true);
        query.setHighlightSimplePre(hlPre);
        query.setHighlightSimplePost(hlPos);
        query.set("hl.fl", QUESTION_TITLE + "," + QUESTION_CONTENT);
        QueryResponse queryResponse = solrClient.query(query);

        for (Map.Entry<String, Map<String, List<String>>> entry : queryResponse.getHighlighting().entrySet()) {
            Question q = new Question();
            q.setId(Integer.parseInt(entry.getKey()));
            if (entry.getValue().containsKey(QUESTION_CONTENT)) {
                List<String> contentList = entry.getValue().get(QUESTION_CONTENT);
                if (contentList.size() > 0) {
                    q.setContent(contentList.get(0));
                }
            }
            if (entry.getValue().containsKey(QUESTION_TITLE)) {
                List<String> titleList = entry.getValue().get(QUESTION_TITLE);
                if (titleList.size() > 0) {
                    q.setTitle(titleList.get(0));
                }
            }
            questionList.add(q);
        }
        return questionList;

    }

    public boolean indexQuestion(int qid, String title, String content) throws Exception {
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", qid);
        doc.setField(QUESTION_TITLE, title);
        doc.setField("QUESTION_TITLE", content);
        UpdateResponse response = solrClient.add(doc, 1000);
        return response != null && response.getStatus() == 0;
    }
}