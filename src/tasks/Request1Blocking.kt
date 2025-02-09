package tasks

import contributors.*
import retrofit2.Response

fun loadContributorsBlocking(service: GitHubService, req: RequestData): List<User> {
    val repos = service
        // return an instance of the *Call class (#1). At this point, no request is sent.
        .getOrgReposCall(req.org)
        // *Call.execute() is then invoked to perform the request (#2). execute() is a synchronous call that blocks the underlying thread.
        .execute() // Executes request and blocks the current thread
        // When you get the response, the result is logged by calling the specific logRepos() and logUsers() functions (#3). If the HTTP response contains an error, this error will be logged here.
        .also { logRepos(req, it) }
        // Finally, get the response's body, which contains the data you need. For this tutorial, you'll use an empty list as a result in case there is an error, and you'll log the corresponding error (#4).
        .bodyList()

    return repos.flatMap { repo ->
        service
            .getRepoContributorsCall(req.org, repo.name)
            .execute() // Executes request and blocks the current thread
            .also { logUsers(repo, it) }
            .bodyList()
    }.aggregate()
}

fun <T> Response<List<T>>.bodyList(): List<T> {
    return body() ?: emptyList()
}
