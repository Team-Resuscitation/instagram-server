package     com.resuscitation.instagram.auth.oauth

abstract class OAuth2UserInfo(
    var attributes: Map<String, Any>,
) {
    /** 프로바이더 ID */
    abstract val id: String
    abstract val name: String?
    abstract val email: String?
    abstract val imageUrl: String?
}
