package maxdistructo.discord.core.jda.message

import maxdistructo.discord.core.jda.Client
import net.dv8tion.jda.core.requests.Request
import net.dv8tion.jda.core.requests.Response
import net.dv8tion.jda.core.requests.RestAction
import net.dv8tion.jda.core.requests.Route
import net.dv8tion.jda.core.entities.Channel
import net.dv8tion.jda.webhook.WebhookClientBuilder
import org.json.JSONObject

class RestRequestCreateWebhook(channel : Channel, name : String, avatar : String) : RestAction<WebhookClientBuilder>(Client.client, Route.Channels.CREATE_WEBHOOK.compile(channel.id), JSONObject().put("name", name).put("avatar", avatar)) {
    override fun handleResponse(response: Response, request: Request<WebhookClientBuilder>){
        if (!response.isOk) {
            request.onFailure(response)
            return
        }
        val webhookJson = response.`object`
        request.onSuccess(WebhookClientBuilder(webhookJson.getLong("id"), webhookJson.getString("token")))
    }
}