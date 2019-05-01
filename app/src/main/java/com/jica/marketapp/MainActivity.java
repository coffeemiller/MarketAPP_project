package com.jica.marketapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView; //불러온 상품 데이터 보여줄곳

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        editText = (EditText) findViewById(R.id.goodsInput);

    }

    //전체검색 버튼 클릭
    public void onButton1Clicked(View v) {
        Toast.makeText(getApplicationContext(), "전체검색 버튼 클릭", Toast.LENGTH_LONG).show();
        new ApiCallAsyncTask().execute();  //
    }

    //검색 동기 태스크
    public class ApiCallAsyncTask extends AsyncTask<String, Void, Document> {
        ProgressDialog asyncDialog = new ProgressDialog(MainActivity.this); //로딩바 객체 선언

        @Override
        protected void onPreExecute() {//Background 작업 시작전에 UI 작업을 진행 한다.
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중... 잠시만 기다려~");
            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Document doInBackground(String... urls) { //백그라운드 작업
            URL url;
            Document doc = null;
            try {
                //OPEN API 주소와 인증키로 XML 문서를 불러온다
                url = new URL("http://openapi.price.go.kr/openApiImpl/ProductPriceInfoService/getProductInfoSvc.do?ServiceKey=PvaWAkEzMy0sTl6%2BKWbraerdXDYtL1mJYbF2KVvBy8fwNdJdb7SQXb8X3kIccNSw8YJkk%2B66M6xcZMur3PGQlA%3D%3D");
                //url = new URL("http://openapi.price.go.kr/openApiImpl/ProductPriceInfoService/getProductInfoSvc.do?goodId=5&ServiceKey=PvaWAkEzMy0sTl6%2BKWbraerdXDYtL1mJYbF2KVvBy8fwNdJdb7SQXb8X3kIccNSw8YJkk%2B66M6xcZMur3PGQlA%3D%3D");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Log.d("========",""+"Parsing Error");
            }
            return doc;
        }

        //Background 작업이 끝난 후 데이터를 받아 UI 작업을 진행 한다.
        @Override
        protected void onPostExecute(Document result) {
            ArrayList<goodsTable> goods = new ArrayList<goodsTable>();

            String s = "";
            Element root = result.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("item");
            //Log.d("========",""+nodeList.getLength());
            for(int i = 0; i< nodeList.getLength(); i++){
                String Name="", Id="", EntpCode="", UnitDivCode="", BaseCnt="", SmlclsCode="", Mean="", TotalCnt="",
                        TotalDivCode="";

                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;
                NodeList idx = fstElmnt.getElementsByTagName("goodId");
                if(idx.item(0) != null){
                    s += "goodId = "+  idx.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                    Id = idx.item(0).getChildNodes().item(0).getNodeValue();
                }

                NodeList gugun = fstElmnt.getElementsByTagName("goodName");
                if(gugun.item(0) != null){
                    s += "goodName = "+  gugun.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                    Name = gugun.item(0).getChildNodes().item(0).getNodeValue();
                }

                NodeList instt  = fstElmnt.getElementsByTagName("productEntpCode");
                if(instt.item(0) != null){
                    s += "productEntpCode = "+ instt.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                    EntpCode = instt.item(0).getChildNodes().item(0).getNodeValue();
                }

                NodeList spot = fstElmnt.getElementsByTagName("goodUnitDivCode");
                if(spot.item(0) != null){
                    s += "goodUnitDivCode = "+  spot.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                    UnitDivCode = spot.item(0).getChildNodes().item(0).getNodeValue();
                }

                NodeList spotGubun = fstElmnt.getElementsByTagName("goodBaseCnt");
                if(spotGubun.item(0) != null){
                    s += "goodBaseCnt = "+  spotGubun.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                    BaseCnt = spotGubun.item(0).getChildNodes().item(0).getNodeValue();
                }

                NodeList airPump = fstElmnt.getElementsByTagName("goodSmlclsCode");
                if(airPump.item(0) != null){
                    s += "goodSmlclsCode = "+  airPump.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                    SmlclsCode = airPump.item(0).getChildNodes().item(0).getNodeValue();
                }
                Log.d("========",""+i);
                NodeList updtDate = fstElmnt.getElementsByTagName("detailMean");
                if(updtDate.item(0) != null){
                    s += "detailMean = "+  updtDate.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                    Mean = updtDate.item(0).getChildNodes().item(0).getNodeValue();
                }

                NodeList aDate = fstElmnt.getElementsByTagName("goodTotalCnt");
                if(aDate.item(0) != null){
                    s += "goodTotalCnt = "+  aDate.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                    TotalCnt = aDate.item(0).getChildNodes().item(0).getNodeValue();
                }

                NodeList bDate = fstElmnt.getElementsByTagName("goodTotalDivCode");
                if(bDate.item(0) != null){
                    s += "goodTotalDivCode = "+  bDate.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                    TotalDivCode = bDate.item(0).getChildNodes().item(0).getNodeValue();
                }

                goods.add(new goodsTable(Id, Name, EntpCode, UnitDivCode, BaseCnt, SmlclsCode, Mean, TotalCnt, TotalDivCode));
            }

            textView.setText(s);   //화면에 데이터를 넣는다

            super.onPostExecute(result);
            if (asyncDialog != null) {
                asyncDialog.cancel();   //로딩바 제거
            }
        }
    }


    public void onButton2Clicked(View v) {
        int index = 0;
        String s = "";
        String findName = editText.getText().toString();
        Toast.makeText(getApplicationContext(), "상품검색 버튼 클릭 : "+findName, Toast.LENGTH_LONG).show();

        goodsTable fGoods = new goodsTable();
        fGoods.name = findName;

        ArrayList<goodsTable> goods = new ArrayList<goodsTable>();

        if (goods.contains(fGoods)) {
            index = goods.indexOf(fGoods);
            System.out.println(goods.get(index));
        }
        s += findName;
        textView.setText(s);   //화면에 데이터를 넣는다
    }

}
