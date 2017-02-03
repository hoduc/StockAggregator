package com.stockaggregator;

import android.os.AsyncTask;

/**
 * Created by ducho on 4/16/16.
 */
public class AsyncTest {

    public static Integer integer = 1;

    public static synchronized void incI(){
        integer += 1;
    }


    public static final Runnable runTask = new Runnable() {
        @Override
        public void run() {
            incI();
        }
    };


    public static void main( String[] args ) {
        new AsyncTask<Runnable,Void,Integer>(){
            @Override
            protected Integer doInBackground(Runnable... params) {
                for ( Runnable r : params )
                {
                    r.run();
                }
                return 0;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                System.out.println(integer);
            }
        }.execute(runTask, runTask, runTask);
    }
}
