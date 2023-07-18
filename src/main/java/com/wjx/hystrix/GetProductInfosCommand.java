package com.wjx.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.wjx.util.JsonUtil;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

import java.io.Serializable;

/**
 * @Author wangjiaxing
 * @Date 2023/6/25
 */
public class GetProductInfosCommand extends HystrixObservableCommand<GetProductInfosCommand.ProductInfo> {

    private String[] productIds;

    public GetProductInfosCommand(String[] productIds) {
        // 还是绑定在同一个线程池
        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"));
        this.productIds = productIds;
    }

    @Override
    protected Observable<ProductInfo> construct() {
        return Observable.unsafeCreate((Observable.OnSubscribe<ProductInfo>) subscriber -> {
            for (String productId : productIds) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 批量获取商品数据
//                String url = "http://localhost:8081/getProductInfo?productId=" + productId;
//                String response = HttpClientUtils.sendGetRequest(url);
//                ProductInfo productInfo = JSONObject.parseObject(response, ProductInfo.class);
                ProductInfo productInfo = new ProductInfo(productId);
                System.out.println("produce : " + JsonUtil.toString(productInfo));

                subscriber.onNext(productInfo);
            }
            subscriber.onCompleted();

        }).subscribeOn(Schedulers.io());
    }


    public static void main(String[] args) throws InterruptedException {
        GetProductInfosCommand getProductInfosCommand = new GetProductInfosCommand("p1,p2,p3".split(","));
        final Observable<ProductInfo> observable = getProductInfosCommand.observe();
        observable.subscribe(new Observer<ProductInfo>() {
            @Override
            public void onCompleted() {

                System.out.println("things completed");
            }

            @Override
            public void onError(Throwable e) {

                System.out.println("some error");
            }

            @Override
            public void onNext(ProductInfo productInfo) {
                System.out.println("productId: " + JsonUtil.toString(productInfo));
            }
        });
        Thread.sleep(1000000);
    }

    class ProductInfo implements Serializable {
        String name;

        public ProductInfo(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
