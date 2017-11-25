package io.haemmi.brightness2;

import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class RetrofitExampleTest {
    @Test
    public void getExample() throws Exception {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://jsonplaceholder.typicode.com/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        FakeApi fakeApi = retrofit.create(FakeApi.class);
        Call<List<Post>> postsCall = fakeApi.listPosts();
        Response<List<Post>> postsResponse = postsCall.execute();

        assert(postsResponse.isSuccessful());

        System.out.println(postsResponse.raw());
        for(Post post : postsResponse.body()) {
            System.out.println(post.title);
        }
    }

    interface FakeApi {
        @GET("posts")
        Call <List<Post>> listPosts();
    }

    class Post {
        long id;
        long userid;
        String title;
        String body;
    }
}