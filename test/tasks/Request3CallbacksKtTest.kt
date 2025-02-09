package tasks

import contributors.MockGithubService
import contributors.expectedResults
import contributors.testRequestData
import org.junit.Assert
import org.junit.Test

class Request3CallbacksKtTest {
    @Test
    fun testDataIsLoaded() {
        loadContributorsCallbacks(MockGithubService, testRequestData) {
            // このassertEqualsの仕方おもしろい
            Assert.assertEquals(
                "Wrong result for 'loadContributorsCallbacks'",
                expectedResults.users, it
            )
            // 第3引数はupdateResults関数を受け取ることになっているところにassertEqualsを渡してる
            // updateResultsは引数にloadしたContributorsを引数に受け取る作りになっている
            // そのためitが指定された引数になってるっぽい？(なんでやちゃんと理解できてない)
            // なのでitで期待値/Contributorsをここで指定できてる
        }
    }
}
