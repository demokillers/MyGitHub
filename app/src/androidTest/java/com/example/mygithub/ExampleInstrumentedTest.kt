package com.example.mygithub

import android.content.Intent
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.MyGitHub.R
import com.example.mygithub.view.activity.LoginActivity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun testPackageName() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        //检查包名
        assertEquals("com.example.MyGitHub", appContext.packageName)
    }

    @Test
    fun testStartMainActivity() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(appContext, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent)
        val loginView = activity.findViewById<TextView>(R.id.login)
        //是否成功启动登录页
        assertEquals(appContext.getString(R.string.login), loginView.text)
        activity.runOnUiThread {
            //点击登录
            loginView.performClick()
        }
        val mainActivityMonitor = InstrumentationRegistry.getInstrumentation().addMonitor(
            "com.example.mygithub.view.activity.MainActivity", null, false
        )
        //设定等待满足要求的活动创建成功，最多等待5s
        mainActivityMonitor.waitForActivityWithTimeout(5000)
        //活动创建成功，am.getHits()值为1，否则为0
        assertEquals(1, mainActivityMonitor.hits)
        sleep(3000)
    }

    @Test
    fun testStartSearchActivity() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(appContext, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent)
        val loginView = activity.findViewById<TextView>(R.id.login)
        val searchView = activity.findViewById<TextView>(R.id.search)
        //是否成功启动登录页
        assertEquals(appContext.getString(R.string.login), loginView.text)
        activity.runOnUiThread {
            //点击搜索
            searchView.performClick()
        }
        val searchActivityMonitor = InstrumentationRegistry.getInstrumentation().addMonitor(
            "com.example.mygithub.view.activity.SearchActivity", null, false
        )
        //设定等待满足要求的活动创建成功，最多等待5s
        searchActivityMonitor.waitForActivityWithTimeout(5000)
        //活动创建成功，am.getHits()值为1，否则为0
        assertEquals(1, searchActivityMonitor.hits)
        sleep(3000)
    }
}