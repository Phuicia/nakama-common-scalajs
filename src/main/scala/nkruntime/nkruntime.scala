// Copyright 2021 The Nakama Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package nkruntime

import scala.scalajs.js

val runtimeVersion = "V1.26.0"

/**
 * System User
 */
val SystemUserId = "00000000-0000-0000-0000-000000000000"

/**
 * The context of the current execution; used to observe and pass on cancellation signals.
 */
trait Context extends js.Object {
  val env: js.Dictionary[String]
  val executionMode: String
  val node: String
  val version: String
  val headers: js.Dictionary[js.Array[String]]
  val queryParams: js.Dictionary[js.Array[String]]
  val userId: String
  val username: String
  val vars: js.Dictionary[String]
  val userSessionExp: Number
  val sessionId: String
  val clientIp: String
  val clientPort: String
  val matchId: String
  val matchNode: String
  val matchLabel: String
  val matchTickRate: Number
  val lang: String
}

type ReadPermissionValues = 0 | 1 | 2
type WritePermissionValues = 0 | 1

/**
 * GRPC Error codes supported for thrown custom errors.
 *
 * These errors map to HTTP status codes as shown here: https://github.com/grpc/grpc/blob/master/doc/http-grpc-status-mapping.md/.
 */
object Codes {

  type Type = 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16

  /**
   * The operation was cancelled, typically by the caller.
   */
  val CANCELLED: Type = 1

  /**
   * Unknown error. For example, this error may be returned when a Status value received from another address space belongs to an error space that is not known in this address space. Also errors raised by APIs that do not return enough error information may be converted to this error.
   */
  val UNKNOWN: Type = 2

  /**
   * The client specified an invalid argument. Note that this differs from FAILED_PRECONDITION. INVALID_ARGUMENT indicates arguments that are problematic regardless of the state of the system (e.g., a malformed file name).
   */
  val INVALID_ARGUMENT: Type = 3

  /**
   * The deadline expired before the operation could complete. For operations that change the state of the system, this error may be returned even if the operation has completed successfully. For example, a successful response from a server could have been delayed long
   */
  val DEADLINE_EXCEEDED: Type = 4

  /**
   * Some requested entity (e.g., file or directory) was not found. Note to server developers: if a request is denied for an entire class of users, such as gradual feature rollout or undocumented allowlist, NOT_FOUND may be used. If a request is denied for some users within a class of users, such as user-based access control, PERMISSION_DENIED must be used.
   */
  val NOT_FOUND: Type = 5

  /**
   * The entity that a client attempted to create (e.g., file or directory) already exists.
   */
  val ALREADY_EXISTS: Type = 6

  /**
   * The caller does not have permission to execute the specified operation. PERMISSION_DENIED must not be used for rejections caused by exhausting some resource (use RESOURCE_EXHAUSTED instead for those errors). PERMISSION_DENIED must not be used if the caller can not be identified (use UNAUTHENTICATED instead for those errors). This error code does not imply the request is valid or the requested entity exists or satisfies other pre-conditions.
   */
  val PERMISSION_DENIED: Type = 7

  /**
   * Some resource has been exhausted, perhaps a per-user quota, or perhaps the entire file system is out of space.
   */
  val RESOURCE_EXHAUSTED: Type = 8

  /**
   * The operation was rejected because the system is not in a state required for the operation's execution. For example, the directory to be deleted is non-empty, an rmdir operation is applied to a non-directory, etc. Service implementors can use the following guidelines to decide between FAILED_PRECONDITION, ABORTED, and UNAVAILABLE: (a) Use UNAVAILABLE if the client can retry just the failing call. (b) Use ABORTED if the client should retry at a higher level (e.g., when a client-specified test-and-set fails, indicating the client should restart a read-modify-write sequence). (c) Use FAILED_PRECONDITION if the client should not retry until the system state has been explicitly fixed. E.g., if an "rmdir" fails because the directory is non-empty, FAILED_PRECONDITION should be returned since the client should not retry unless the files are deleted from the directory.
   */
  val FAILED_PRECONDITION: Type = 9

  /**
   * The operation was aborted, typically due to a concurrency issue such as a sequencer check failure or transaction abort. See the guidelines above for deciding between FAILED_PRECONDITION, ABORTED, and UNAVAILABLE.
   */
  val ABORTED: Type = 10

  /**
   * The operation was attempted past the valid range. E.g., seeking or reading past end-of-file. Unlike INVALID_ARGUMENT, this error indicates a problem that may be fixed if the system state changes. For example, a 32-bit file system will generate INVALID_ARGUMENT if asked to read at an offset that is not in the range [0,2^32-1], but it will generate OUT_OF_RANGE if asked to read from an offset past the current file size. There is a fair bit of overlap between FAILED_PRECONDITION and OUT_OF_RANGE. We recommend using OUT_OF_RANGE (the more specific error) when it applies so that callers who are iterating through a space can easily look for an OUT_OF_RANGE error to detect when they are done.
   */
  val OUT_OF_RANGE: Type = 11

  /**
   * The operation is not implemented or is not supported/enabled in this service.
   */
  val UNIMPLEMENTED: Type = 12

  /**
   * Internal errors. This means that some invariants expected by the underlying system have been broken. This error code is reserved for serious errors.
   */
  val INTERNAL: Type = 13

  /**
   * The service is currently unavailable. This is most likely a transient condition, which can be corrected by retrying with a backoff. Note that it is not always safe to retry non-idempotent operations.
   */
  val UNAVAILABLE: Type = 14

  /**
   * Unrecoverable data loss or corruption.
   */
  val DATA_LOSS: Type = 15

  /**
   * The request does not have valid authentication credentials for the operation.
   */
  val UNAUTHENTICATED: Type = 16

}

/**
 * A custom Runtime Error
 */
trait Error extends js.Object {
  val message: String
  val code: Codes.Type
}


/**
 * An RPC function definition.
 */
trait RpcFunction extends js.Function {
  /**
   * An RPC function to be executed when called by ID.
   *
   * @param ctx     - The context for the execution.
   * @param logger  - The server logger.
   * @param nk      - The Nakama server APIs.
   * @param payload - The input data to the function call. This is usually an escaped JSON object.
   * @return A response payload or error if one occurred.
   * @throws TypeError
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, payload: String): String | Unit
}

/**
 * A Before Hook function definition.
 */
trait BeforeHookFunction[T] extends js.Function {
  /**
   * A Register Hook function definition.
   *
   * The function must return the T payload as this is what will be passed on to the hooked function.
   * Return null to bail out of executing the function instead.
   *
   * @param ctx    - The context for the execution.
   * @param logger - The server logger.
   * @param nk     - The Nakama server APIs.
   * @param data   - The input data to the function call.
   * @return The escaped JSON payload.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, data: T): T | Unit
}

/**
 * A After Hook function definition.
 */
trait AfterHookFunction[T, K] extends js.Function {
  /**
   * A Register Hook function definition.
   *
   * @param ctx     - The context for the execution.
   * @param logger  - The server logger.
   * @param nk      - The Nakama server APIs.
   * @param data    - The data returned by the function call.
   * @param request - The request payload.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, data: T, request: K): Unit
}

/**
 * A realtime before hook function definition.
 */
trait RtBeforeHookFunction[T <: Envelope] extends js.Function {
  /**
   * A Register Hook function definition.
   *
   * The function must return the T payload as this is what will be passed on to the hooked function.
   * Return null to bail out of executing the function instead.
   *
   * @param ctx      - The context for the execution.
   * @param logger   - The server logger.
   * @param nk       - The Nakama server APIs.
   * @param envelope - The Envelope message received by the function.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, envelope: T): T | Unit
}

/**
 * A realtime after hook function definition.
 */
trait RtAfterHookFunction[T <: Envelope] extends js.Function {
  /**
   * A Register Hook function definition.
   *
   * @param ctx    - The context for the execution.
   * @param logger - The server logger.
   * @param nk     - The Nakama server APIs.
   * @param output - The response envelope, if any.
   * @param input  - The Envelope message received by the function.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, output: T | Null, input: T): Unit
}

/**
 * Matchmaker matched hook function definition.
 */
trait MatchmakerMatchedFunction extends js.Function {
  /**
   * A Matchmaker matched register hook function definition.
   *
   * Expected to return an authoritative match ID for a match ready to receive
   * these users, or void if the match should proceed through the peer-to-peer relayed mode.
   *
   * @param ctx     - The context for the execution.
   * @param logger  - The server logger.
   * @param nk      - The Nakama server APIs.
   * @param matches - The matched users presences and properties.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, matches: js.Array[MatchmakerResult]): String | Unit
}

/**
 * Tournament end hook function definition.
 */
trait TournamentEndFunction extends js.Function {
  /**
   * A Tournament end register hook function definition.
   *
   * @param ctx        - The context for the execution.
   * @param logger     - The server logger.
   * @param nk         - The Nakama server APIs.
   * @param tournament - The ended tournament.
   * @param end        - End time unix timestamp.
   * @param reset      - Reset time unix timestamp.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, tournament: Tournament, end: Number, reset: Number): Unit
}

/**
 * Tournament reset hook function definition.
 */
trait TournamentResetFunction extends js.Function {
  /**
   * A Tournament reset register hook function definition.
   *
   * @param ctx        - The context for the execution.
   * @param logger     - The server logger.
   * @param nk         - The Nakama server APIs.
   * @param tournament - The reset tournament.
   * @param end        - End time unix timestamp.
   * @param reset      - Reset time unix timestamp.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, tournament: Tournament, end: Number, reset: Number): Unit
}

/**
 * Leaderboard reset hook function definition.
 */
trait LeaderboardResetFunction extends js.Function {
  /**
   * A Leaderboard reset register hook function definition.
   *
   * @param ctx         - The context for the execution.
   * @param logger      - The server logger.
   * @param nk          - The Nakama server APIs.
   * @param leaderboard - The reset leaderboard.
   * @param reset       - Reset time unix timestamp.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, leaderboard: Leaderboard, reset: Number): Unit
}

/**
 * Purchase Notification Apple function definition.
 */
trait PurchaseNotificationAppleFunction extends js.Function {
  /**
   * A Purchase Notification Apple register hook function definition.
   *
   * @param ctx             - The context for the execution.
   * @param logger          - The server logger.
   * @param nk              - The Nakama server APIs.
   * @param purchase        - The notification purchase.
   * @param providerPayload - The raw payload of the provider notificaton.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, purchase: ValidatedPurchase, providerPayload: String): Unit
}

/**
 * Subscription Notification Apple function definition.
 */
trait SubscriptionNotificationAppleFunction extends js.Function {
  /**
   * A Subscription Notification Apple register hook function definition.
   *
   * @param ctx             - The context for the execution.
   * @param logger          - The server logger.
   * @param nk              - The Nakama server APIs.
   * @param subscription    - The notification subscription.
   * @param providerPayload - The raw payload of the provider notificaton.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, subscription: ValidatedSubscription, providerPayload: String): Unit
}

/**
 * Purchase Notification Google function definition.
 */
trait PurchaseNotificationGoogleFunction extends js.Function {
  /**
   * A Purchase Notification Google register hook function definition.
   *
   * @param ctx             - The context for the execution.
   * @param logger          - The server logger.
   * @param nk              - The Nakama server APIs.
   * @param purchase        - The notification purchase.
   * @param providerPayload - The raw payload of the provider notificaton.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, purchase: ValidatedPurchase, providerPayload: String): Unit
}

/**
 * Subscription Notification Google function definition.
 */
trait SubscriptionNotificationGoogleFunction extends js.Function {
  /**
   * A Subscription Notification Google register hook function definition.
   *
   * @param ctx             - The context for the execution.
   * @param logger          - The server logger.
   * @param nk              - The Nakama server APIs.
   * @param subscription    - The notification subscription.
   * @param providerPayload - The raw payload of the provider notificaton.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, subscription: ValidatedSubscription, providerPayload: String): Unit
}

/**
 * Match Dispatcher API definition.
 */
trait MatchDispatcher extends js.Function {
  /**
   * Broadcast a message to match presences.
   *
   * @param opcode    - Numeric message op code.
   * @param data      - Opt. Data payload string, or null.
   * @param presences - Opt. List of presences (a subset of match participants) to use as message targets, or null to send to the whole match. Defaults to null.
   * @param sender    - Opt. A presence to tag on the message as the 'sender', or null.
   * @param reliable  - Opt. Broadcast the message with delivery guarantees or not. Defaults to true.
   * @throws TypeError
   * @throws GoError
   */
  def broadcastMessage(opcode: Number, data: js.UndefOr[js.typedarray.ArrayBuffer | String | Null] = js.undefined, presences: js.UndefOr[Array[Presence] | Null] = js.undefined, sender: js.UndefOr[Presence | Null] = js.undefined, reliable: js.UndefOr[Boolean] = js.undefined): Unit

  /**
   * Defer message broadcast to match presences.
   *
   * @param opcode    - Numeric message op code.
   * @param data      - Opt. Data payload string, or null.
   * @param presences - Opt. List of presences (a subset of match participants) to use as message targets, or null to send to the whole match. Defaults to null
   * @param sender    - Opt. A presence to tag on the message as the 'sender', or null.
   * @param reliable  - Opt. Broadcast the message with delivery guarantees or not. Defaults to true.
   * @throws TypeError
   * @throws GoError
   */
  def broadcastMessageDeferred(opcode: Number, data: js.UndefOr[js.typedarray.ArrayBuffer | String | Null], presences: js.UndefOr[Array[Presence] | Null], sender: js.UndefOr[Presence | Null], reliable: js.UndefOr[Boolean]): Unit

  /**
   * Kick presences from match.
   *
   * @param presences - List of presences to kick from the match.
   * @throws TypeError
   * @throws GoError
   */
  def matchKick(presences: js.Array[String]): Unit

  /**
   * Update match label.
   *
   * @param label - New label for the match.
   * @throws TypeError
   * @throws GoError
   */
  def matchLabelUpdate(label: String): Unit
}

type SessionVars = js.Dictionary[String]

/**
 * Match Message definition
 */
trait MatchMessage extends js.Object {
  val sender: Presence
  val persistence: Boolean
  val status: String
  val opCode: Number
  val data: js.typedarray.ArrayBuffer
  val reliable: Boolean
  val receiveTimeMs: Number
}

/**
 * Match state definition
 */
type MatchState = js.Object

/**
 * Hooks payloads definitions
 */
trait AccountApple extends js.Object {
  val token: js.UndefOr[String] = js.undefined
  val vars: js.UndefOr[SessionVars] = js.undefined
}

trait AuthenticateAppleRequest extends js.Object {
  val account: js.UndefOr[AccountApple] = js.undefined
  val create: js.UndefOr[Boolean] = js.undefined
  val username: js.UndefOr[String] = js.undefined
}

trait AccountCustom extends js.Object {
  val id: js.UndefOr[String] = js.undefined
  val vars: js.UndefOr[SessionVars] = js.undefined
}

trait AuthenticateCustomRequest extends js.Object {
  val account: js.UndefOr[AccountCustom] = js.undefined
  val create: js.UndefOr[Boolean] = js.undefined
  val username: js.UndefOr[String] = js.undefined
}

trait AuthenticateDeviceRequest extends js.Object {
  val account: js.UndefOr[AccountDevice] = js.undefined
  val create: js.UndefOr[Boolean] = js.undefined
  val username: js.UndefOr[String] = js.undefined
}

trait AccountEmail extends js.Object {
  val email: js.UndefOr[String] = js.undefined
  val password: js.UndefOr[String] = js.undefined
  val vars: js.UndefOr[SessionVars] = js.undefined
}

trait AuthenticateEmailRequest extends js.Object {
  val account: AccountEmail
  val create: Boolean
  val username: String
}

trait AuthenticateFacebookRequest extends js.Object {
  val account: js.UndefOr[AccountFacebook] = js.undefined
  val create: js.UndefOr[Boolean] = js.undefined
  val username: js.UndefOr[String] = js.undefined
  val sync: js.UndefOr[Boolean] = js.undefined
}

trait AccountFacebook extends js.Object {
  val token: js.UndefOr[String] = js.undefined
  val vars: js.UndefOr[SessionVars] = js.undefined
}

trait AccountFacebookInstantGame extends js.Object {
  val signedPlayerInfo: js.UndefOr[String] = js.undefined
  val vars: js.UndefOr[SessionVars] = js.undefined
}

trait AuthenticateFacebookInstantGameRequest extends js.Object {
  val account: js.UndefOr[AccountFacebookInstantGame] = js.undefined
  val create: js.UndefOr[Boolean] = js.undefined
  val username: js.UndefOr[String] = js.undefined
}

trait AccountGameCenter extends js.Object {
  val playerId: js.UndefOr[String] = js.undefined
  val bundleId: js.UndefOr[String] = js.undefined
  val timestampSeconds: js.UndefOr[String] = js.undefined
  val salt: js.UndefOr[String] = js.undefined
  val signature: js.UndefOr[String] = js.undefined
  val publicKeyUrl: js.UndefOr[String] = js.undefined
  val vars: js.UndefOr[SessionVars] = js.undefined
}

trait AuthenticateGameCenterRequest extends js.Object {
  val account: js.UndefOr[AccountGameCenter] = js.undefined
  val create: js.UndefOr[Boolean] = js.undefined
  val username: js.UndefOr[String] = js.undefined
}

trait AccountGoogle extends js.Object {
  val token: js.UndefOr[String] = js.undefined
  val vars: js.UndefOr[SessionVars] = js.undefined
}

trait AuthenticateGoogleRequest extends js.Object {
  val account: js.UndefOr[AccountGoogle] = js.undefined
  val create: js.UndefOr[Boolean] = js.undefined
  val username: js.UndefOr[String] = js.undefined
}

trait AccountSteam extends js.Object {
  val token: js.UndefOr[String] = js.undefined
  val vars: js.UndefOr[SessionVars] = js.undefined
}

trait AuthenticateSteamRequest extends js.Object {
  val account: js.UndefOr[AccountSteam] = js.undefined
  val create: js.UndefOr[Boolean] = js.undefined
  val username: js.UndefOr[String] = js.undefined
}

trait ListChannelMessagesRequest extends js.Object {
  val channelId: js.UndefOr[String] = js.undefined
  val limit: js.UndefOr[Number] = js.undefined
  val forward: js.UndefOr[Boolean] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
}

trait ChannelMessage extends js.Object {
  val channelId: js.UndefOr[String] = js.undefined
  val messageId: js.UndefOr[String] = js.undefined
  val code: js.UndefOr[Number] = js.undefined
  val senderId: js.UndefOr[String] = js.undefined
  val username: js.UndefOr[String] = js.undefined
  val content: js.UndefOr[String] = js.undefined
  val createTime: js.UndefOr[Number] = js.undefined
  val updateTime: js.UndefOr[Number] = js.undefined
  val persistent: js.UndefOr[Boolean] = js.undefined
  val roomName: js.UndefOr[String] = js.undefined
  val groupId: js.UndefOr[String] = js.undefined
  val userIdOne: js.UndefOr[String] = js.undefined
  val userIdTwo: js.UndefOr[String] = js.undefined
}

trait ListFriendsRequest extends js.Object {
  val limit: js.UndefOr[Number] = js.undefined
  val state: js.UndefOr[Number] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
}

trait AddFriendsRequest extends js.Object {
  val ids: js.UndefOr[js.Array[String]] = js.undefined
  val usernames: js.UndefOr[js.Array[String]] = js.undefined
}

trait DeleteFriendsRequest extends js.Object {
  val ids: js.UndefOr[js.Array[String]] = js.undefined
  val usernames: js.UndefOr[js.Array[String]] = js.undefined
}

trait BlockFriendsRequest extends js.Object {
  val ids: js.UndefOr[js.Array[String]] = js.undefined
  val usernames: js.UndefOr[js.Array[String]] = js.undefined
}

trait ImportFacebookFriendsRequest extends js.Object {
  val account: js.UndefOr[AccountFacebook] = js.undefined
  val reset: js.UndefOr[Boolean] = js.undefined
}

trait ImportSteamFriendsRequest extends js.Object {
  val account: js.UndefOr[AccountSteam] = js.undefined
  val reset: js.UndefOr[Boolean] = js.undefined
}

trait CreateGroupRequest extends js.Object {
  val name: js.UndefOr[String] = js.undefined
  val description: js.UndefOr[String] = js.undefined
  val langTag: js.UndefOr[String] = js.undefined
  val avatarUrl: js.UndefOr[String] = js.undefined
  val open: js.UndefOr[Boolean] = js.undefined
  val maxCount: js.UndefOr[Number] = js.undefined
}

trait UpdateGroupRequest extends js.Object {
  val name: js.UndefOr[String] = js.undefined
  val description: js.UndefOr[String] = js.undefined
  val langTag: js.UndefOr[String] = js.undefined
  val avatarUrl: js.UndefOr[String] = js.undefined
  val open: js.UndefOr[Boolean] = js.undefined
}

trait DeleteGroupRequest extends js.Object {
  val groupId: js.UndefOr[String] = js.undefined
}

trait JoinGroupRequest extends js.Object {
  val groupId: js.UndefOr[String] = js.undefined
}

trait LeaveGroupRequest extends js.Object {
  val groupId: js.UndefOr[String] = js.undefined
}

trait AddGroupUsersRequest extends js.Object {
  val groupId: js.UndefOr[String] = js.undefined
  val userIds: js.UndefOr[js.Array[String]] = js.undefined
}

trait BanGroupUsersRequest extends js.Object {
  val groupId: js.UndefOr[String] = js.undefined
  val userIds: js.UndefOr[js.Array[String]] = js.undefined
}

trait KickGroupUsersRequest extends js.Object {
  val groupId: js.UndefOr[String] = js.undefined
  val userIds: js.UndefOr[js.Array[String]] = js.undefined
}

trait PromoteGroupUsersRequest extends js.Object {
  val groupId: js.UndefOr[String]
  val userIds: js.UndefOr[js.Array[String]]
}

trait DemoteGroupUsersRequest extends js.Object {
  val groupId: js.UndefOr[String] = js.undefined
  val userIds: js.UndefOr[js.Array[String]] = js.undefined
}

trait ListGroupUsersRequest extends js.Object {
  val groupId: js.UndefOr[String] = js.undefined
  val limit: js.UndefOr[Number] = js.undefined
  val state: js.UndefOr[Number] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
}

trait ListUserGroupsRequest extends js.Object {
  val userId: js.UndefOr[String] = js.undefined
  val limit: js.UndefOr[Number] = js.undefined
  val state: js.UndefOr[Number] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
}

trait ListGroupsRequest extends js.Object {
  val name: js.UndefOr[String] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
  val limit: js.UndefOr[Number] = js.undefined
}

trait DeleteLeaderboardRecordRequest extends js.Object {
  val leaderboardId: js.UndefOr[String] = js.undefined
}

trait ListLeaderboardRecordsRequest extends js.Object {
  val leaderboardId: js.UndefOr[String] = js.undefined
  val ownerIds: js.UndefOr[js.Array[String]] = js.undefined
  val limit: js.UndefOr[Number] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
  val expiry: js.UndefOr[String] = js.undefined
}

trait WriteLeaderboardRecordRequestLeaderboardRecordWrite extends js.Object {
  val score: js.UndefOr[String] = js.undefined
  val subscore: js.UndefOr[String] = js.undefined
  val metadata: js.UndefOr[String] = js.undefined
}

trait WriteLeaderboardRecordRequest extends js.Object {
  val leaderboardId: js.UndefOr[String] = js.undefined
  val record: js.UndefOr[WriteLeaderboardRecordRequestLeaderboardRecordWrite] = js.undefined
}

trait ListLeaderboardRecordsAroundOwnerRequest extends js.Object {
  val leaderboardId: js.UndefOr[String] = js.undefined
  val limit: js.UndefOr[Number] = js.undefined
  val ownerId: js.UndefOr[String] = js.undefined
  val expiry: js.UndefOr[String] = js.undefined
}

trait AccountAppleVarsEntry extends js.Object {
  val key: js.UndefOr[String] = js.undefined
  val value: js.UndefOr[String] = js.undefined
}

trait AccountCustomVarsEntry extends js.Object {
  val key: js.UndefOr[String] = js.undefined
  val value: js.UndefOr[String] = js.undefined
}

trait LinkFacebookRequest extends js.Object {
  val account: js.UndefOr[AccountFacebook] = js.undefined
  val sync: js.UndefOr[Boolean] = js.undefined
}

trait LinkSteamRequest extends js.Object {
  val account: js.UndefOr[AccountSteam] = js.undefined
  val sync: js.UndefOr[Boolean] = js.undefined
}

trait ListMatchesRequest extends js.Object {
  val limit: js.UndefOr[Number] = js.undefined
  val authoritative: js.UndefOr[Boolean] = js.undefined
  val label: js.UndefOr[String] = js.undefined
  val minSize: js.UndefOr[Number] = js.undefined
  val maxSize: js.UndefOr[Number] = js.undefined
  val query: js.UndefOr[String] = js.undefined
}

trait ListNotificationsRequest extends js.Object {
  val limit: js.UndefOr[Number] = js.undefined
  val cacheableCursor: js.UndefOr[String] = js.undefined
}

trait ListStorageObjectsRequest extends js.Object {
  val collection: String
  val userId: String
  val limit: Number
  val cursor: String
}

trait ReadStorageObjectId extends js.Object {
  val collection: js.UndefOr[String] = js.undefined
  val key: js.UndefOr[String] = js.undefined
  val userId: js.UndefOr[String] = js.undefined
}

trait ReadStorageObjectsRequest extends js.Object {
  val objectIds: js.UndefOr[js.Array[ReadStorageObjectId]] = js.undefined
}

trait WriteStorageObject extends js.Object {
  val collection: js.UndefOr[String] = js.undefined
  val key: js.UndefOr[String] = js.undefined
  val value: js.UndefOr[String] = js.undefined
  val version: js.UndefOr[String] = js.undefined
  val permissionRead: js.UndefOr[Number] = js.undefined
  val permissionWrite: js.UndefOr[Number] = js.undefined
}

trait WriteStorageObjectsRequest extends js.Object {
  val objects: js.UndefOr[js.Array[WriteStorageObject]] = js.undefined
}

trait DeleteStorageObjectId extends js.Object {
  val collection: js.UndefOr[String] = js.undefined
  val key: js.UndefOr[String] = js.undefined
  val version: js.UndefOr[String] = js.undefined
}

trait DeleteStorageObjectsRequest extends js.Object {
  val objectIds: js.UndefOr[js.Array[DeleteStorageObjectId]] = js.undefined
}

trait JoinTournamentRequest extends js.Object {
  val tournamentId: js.UndefOr[String] = js.undefined
}

trait ListTournamentRecordsRequest extends js.Object {
  val tournamentId: js.UndefOr[String] = js.undefined
  val ownerIds: js.UndefOr[js.Array[String]] = js.undefined
  val limit: js.UndefOr[Number] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
  val expiry: js.UndefOr[String] = js.undefined
}

trait ListTournamentsRequest extends js.Object {
  val categoryStart: js.UndefOr[Number] = js.undefined
  val categoryEnd: js.UndefOr[Number] = js.undefined
  val startTime: js.UndefOr[Number] = js.undefined
  val endTime: js.UndefOr[Number] = js.undefined
  val limit: js.UndefOr[Number] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
}

trait WriteTournamentRecordRequest extends js.Object {
  val tournamentId: js.UndefOr[String] = js.undefined
  val record: js.UndefOr[WriteTournamentRecordRequestTournamentRecordWrite] = js.undefined
}

trait WriteTournamentRecordRequestTournamentRecordWrite extends js.Object {
  val score: js.UndefOr[String] = js.undefined
  val subscore: js.UndefOr[String] = js.undefined
  val metadata: js.UndefOr[String] = js.undefined
}

trait ListTournamentRecordsAroundOwnerRequest extends js.Object {
  val tournamentId: js.UndefOr[String] = js.undefined
  val limit: js.UndefOr[Number] = js.undefined
  val ownerId: js.UndefOr[String] = js.undefined
  val expiry: js.UndefOr[String] = js.undefined
}

trait GetUsersRequest extends js.Object {
  val ids: js.UndefOr[js.Array[String]] = js.undefined
  val usernames: js.UndefOr[js.Array[String]] = js.undefined
  val facebookIds: js.UndefOr[js.Array[String]] = js.undefined
}

trait Event extends js.Object {
  val name: js.UndefOr[String] = js.undefined
  val properties: js.UndefOr[js.Array[EventPropertiesEntry]] = js.undefined
  val timestamp: js.UndefOr[String] = js.undefined
  val external: js.UndefOr[Boolean] = js.undefined
}

trait EventPropertiesEntry extends js.Object {
  val key: js.UndefOr[String] = js.undefined
  val value: js.UndefOr[String] = js.undefined
}

trait Session extends js.Object {
  val created: js.UndefOr[Boolean] = js.undefined
  val token: js.UndefOr[String] = js.undefined
  val refreshToken: js.UndefOr[String] = js.undefined
}

trait ChannelMessageList extends js.Object {
  val messages: js.UndefOr[js.Array[ChannelMessage]] = js.undefined
  val nextCursor: js.UndefOr[String] = js.undefined
  val prevCursor: js.UndefOr[String] = js.undefined
  val cacheableCursor: js.UndefOr[String] = js.undefined
}

trait Friend extends js.Object {
  val user: js.UndefOr[User] = js.undefined
  val state: js.UndefOr[Number] = js.undefined
  val updateTime: js.UndefOr[Number] = js.undefined
}

trait FriendList extends js.Object {
  val friends: js.UndefOr[js.Array[Friend]] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
}

object GroupUserState {
  type Type = 0 | 1 | 2 | 3
  val Superadmin: Type = 0
  val Admin: Type = 1
  val Member: Type = 2
  val JoinRequest: Type = 3
}

trait GroupUser extends js.Object {
  val user: User
  val state: js.UndefOr[GroupUserState.Type] = js.undefined
}

trait GroupUserList extends js.Object {
  val groupUsers: js.UndefOr[js.Array[GroupUser]] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
}

trait LeaderboardRecordList extends js.Object {
  val records: js.UndefOr[js.Array[LeaderboardRecord]] = js.undefined
  val ownerRecords: js.UndefOr[js.Array[LeaderboardRecord]] = js.undefined
  val nextCursor: js.UndefOr[String] = js.undefined
  val prevCursor: js.UndefOr[String] = js.undefined
}

trait MatchList extends js.Object {
  val matches: js.Array[Match] = js.undefined
}

trait DeleteNotificationsRequest extends js.Object {
  val ids: js.Array[String] = js.undefined
}

trait StorageObjectList extends js.Object {
  val objects: js.UndefOr[js.Array[StorageObject]] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
}

trait StorageObjects extends js.Object {
  val objects: js.UndefOr[js.Array[StorageObject]] = js.undefined
}

trait TournamentRecordList extends js.Object {
  val records: js.UndefOr[js.Array[LeaderboardRecord]] = js.undefined
  val ownerRecords: js.UndefOr[js.Array[LeaderboardRecord]] = js.undefined
  val prevCursor: js.UndefOr[String] = js.undefined
  val nextCursor: js.UndefOr[String] = js.undefined
}

trait TournamentList extends js.Object {
  val tournaments: js.Array[Tournament]
  val cursor: js.UndefOr[String] = js.undefined
}

trait Users extends js.Object {
  val users: js.Array[Users]
}

trait MatchmakerResult extends js.Object {
  val properties: js.Dictionary[String]
  val presence: Presence
  val partyId: js.UndefOr[String] = js.undefined
}

trait LeaderboardList extends js.Object {
  val leaderboards: js.Array[Leaderboard]
  val cursor: js.UndefOr[String] = js.undefined
}

/**
 * Realtime hook messages
 */
type RtHookMessage = "ChannelJoin" | "ChannelLeave" | "ChannelMessageSend" | "ChannelMessageUpdate" | "ChannelMessageRemove" | "MatchCreate" | "MatchDataSend" | "MatchJoin" | "MatchLeave" | "MatchmakerAdd" | "MatchmakerRemove" | "PartyCreate" | "PartyJoin" | "PartyLeave" | "PartyPromote" | "PartyAccept" | "PartyRemove" | "PartyClose" | "PartyJoinRequestList" | "PartyMatchmakerAdd" | "PartyMatchmakerRemove" | "PartyDataSend" | "StatusFollow" | "StatusUnfollow" | "StatusUpdate" | "Ping" | "Pong"

/**
 * Match handler definitions
 */
trait MatchHandler[State <: MatchState] extends js.Object {
  val matchInit: MatchInitFunction[State]
  val matchJoinAttempt: MatchJoinAttemptFunction[State]
  val matchJoin: MatchJoinFunction[State]
  val matchLeave: MatchLeaveFunction[State]
  val matchLoop: MatchLoopFunction[State]
  val matchTerminate: MatchTerminateFunction[State]
  val matchSignal: MatchSignalFunction[State]
}

/**
 * Match initialization function definition.
 */
trait MatchInitFunction[State <: MatchState] extends js.Function {
  /**
   * Match initialization function definition.
   *
   * @param ctx    - The context for the execution.
   * @param logger - The server logger.
   * @param nk     - The Nakama server APIs.
   * @param params - Match create http request parameters.
   * @return An object with the match state, tick rate and labels.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, params: js.Dictionary[String]): MatchInitFunctionReturn[State]
}

trait MatchInitFunctionReturn[State] extends js.Object {
  val state: State
  val tickRate: Number
  val label: String
}

/**
 * Match join attempt function definition.
 */
trait MatchJoinAttemptFunction[State <: MatchState] extends js.Function {
  /**
   * User match join attempt function definition.
   *
   * @param ctx        - The context for the execution.
   * @param logger     - The server logger.
   * @param nk         - The Nakama server APIs.
   * @param dispatcher - Message dispatcher APIs.
   * @param tick       - Current match loop tick.
   * @param state      - Current match state.
   * @param presence   - Presence of user attempting to join.
   * @param metadata   - Metadata object.
   * @return object with state, acceptUser and optional rejection message if acceptUser is false.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, dispatcher: MatchDispatcher, tick: Number, state: State, presence: Presence, metadata: js.Dictionary[js.Any]): MatchJoinAttemptFunctionReturn[State] | Null
}

trait MatchJoinAttemptFunctionReturn[State] extends js.Object {
  val state: State
  val accept: Boolean
  val rejectMessage: js.UndefOr[String] = js.undefined
}

/**
 * Match join function definition.
 */
trait MatchJoinFunction[State <: MatchState] extends js.Function {
  /**
   * User match join function definition.
   *
   * @param ctx        - The context for the execution.
   * @param logger     - The server logger.
   * @param nk         - The Nakama server APIs.
   * @param dispatcher - Message dispatcher APIs.
   * @param tick       - Current match loop tick.SSSSS
   * @param state      - Current match state.
   * @param presences  - List of presences.
   * @return object with the new state of the match.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, dispatcher: MatchDispatcher, tick: Number, state: State, presences: js.Array[Presence]): MatchJoinFunctionReturn[State] | Null
}

trait MatchJoinFunctionReturn[State] extends js.Object {
  val state: State
}

/**
 * Match leave function definition.
 */
trait MatchLeaveFunction[State <: MatchState] extends js.Function {
  /**
   * User match leave function definition.
   *
   * @param ctx        - The context for the execution.
   * @param logger     - The server logger.
   * @param nk         - The Nakama server APIs.
   * @param dispatcher - Message dispatcher APIs.
   * @param tick       - Current match loop tick.
   * @param state      - Current match state.
   * @param presences  - List of presences.
   * @return object with the new state of the match.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, dispatcher: MatchDispatcher, tick: Number, state: State, presences: js.Array[Presence]): MatchLeaveFunctionReturn[State] | Null
}

trait MatchLeaveFunctionReturn[State] extends js.Object {
  val state: State
}

/**
 * Match loop function definition.
 */
trait MatchLoopFunction[State <: MatchState] extends js.Function {
  /**
   * User match leave function definition.
   *
   * @param ctx        - The context for the execution.
   * @param logger     - The server logger.
   * @param nk         - The Nakama server APIs.
   * @param dispatcher - Message dispatcher APIs.
   * @param tick       - Current match loop tick.
   * @param state      - Current match state.
   * @param messages   - Received messages in the buffer.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, dispatcher: MatchDispatcher, tick: Number, state: State, messages: js.Array[MatchMessage]): MatchLoopFunctionReturn[State] | Null
}

trait MatchLoopFunctionReturn[State] extends js.Object {
  val state: State
}

/**
 * Match terminate function definition.
 */
trait MatchTerminateFunction[State <: MatchState] extends js.Function {
  /**
   * User match leave function definition.
   *
   * @param ctx          - The context for the execution.
   * @param logger       - The server logger.
   * @param nk           - The Nakama server APIs.
   * @param dispatcher   - Message dispatcher APIs.
   * @param tick         - Current match loop tick.
   * @param state        - Current match state.
   * @param graceSeconds - Number of seconds to gracefully terminate the match. If this time elapses before the function returns the match will be forcefully terminated.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, dispatcher: MatchDispatcher, tick: Number, state: State, graceSeconds: Number): MatchTerminateFunctionReturn[State] | Null
}

trait MatchTerminateFunctionReturn[State] extends js.Object {
  val state: State
}

/**
 * Match signal function definition.
 */
trait MatchSignalFunction[State <: MatchState] extends js.Function {
  /**
   * User match leave function definition.
   *
   * @param ctx        - The context for the execution.
   * @param logger     - The server logger.
   * @param nk         - The Nakama server APIs.
   * @param dispatcher - Message dispatcher APIs.
   * @param tick       - Current match loop tick.
   * @param state      - Current match state.
   * @param data       - Arbitrary data the signal caller is sending to the match signal handler.
   * @return object with state and optional response data string to the signal caller.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, dispatcher: MatchDispatcher, tick: Number, state: State, data: String): MatchSignalFunctionReturn[State] | Null
}

trait MatchSignalFunctionReturn[State] extends js.Object {
  val state: State
  val data: js.UndefOr[String] = js.undefined
}

/**
 * The injector used to initialize features of the game server.
 */
trait Initializer extends js.Object {
  /**
   * Register an RPC function by its ID to be called as a S2S function or by game clients.
   *
   * @param id   - The ID of the function in the server.
   * @param func - The RPC function logic to execute when the RPC is called.
   */
  def registerRpc(id: String, func: RpcFunction): Unit

  /**
   * Register a hook function to be run before an RPC function is invoked.
   * The RPC call is identified by the id param.
   *
   * @param id   - The ID of the RPC function.
   * @param func - The Hook function logic to execute before the RPC is called.
   */
  def registerRtBefore(id: RtHookMessage, func: RtBeforeHookFunction[Envelope]): Unit

  /**
   * Register a hook function to be run after an RPC function is invoked.
   * The RPC call is identified by the id param.
   *
   * @param id   - The ID of the RPC function.
   * @param func - The Hook function logic to execute after the RPC is called.
   */
  def registerRtAfter(id: RtHookMessage, func: RtAfterHookFunction[Envelope]): Unit

  /**
   * Register Before Hook for RPC getAccount function.
   *
   * @param fn - The function to execute before getAccount.
   * @throws TypeError
   */
  def registerBeforeGetAccount(fn: BeforeHookFunction[Unit]): Unit

  /**
   * Register After Hook for RPC getAccount function.
   *
   * @param fn - The function to execute after getAccount.
   * @throws TypeError
   */
  def registerAfterGetAccount(fn: AfterHookFunction[Account, Unit]): Unit

  /**
   * Register before Hook for RPC updateAccount function.
   *
   * @param fn - The function to execute before updateAccount.
   * @throws TypeError
   */
  def registerBeforeUpdateAccount(fn: BeforeHookFunction[UserUpdateAccount]): Unit

  /**
   * Register after Hook for RPC updateAccount function.
   *
   * @param fn - The function to execute after updateAccount.
   * @throws TypeError
   */
  def registerAfterUpdateAccount(fn: AfterHookFunction[Unit, UserUpdateAccount]): Unit

  /**
   * Register before Hook for RPC deleteAccount function.
   *
   * @param fn - The function to execute before updateAccount.
   * @throws TypeError
   */
  def registerBeforeDeleteAccount(fn: BeforeHookFunction[Unit]): Unit

  /**
   * Register after Hook for RPC deleteAccount function.
   *
   * @param fn - The function to execute after updateAccount.
   * @throws TypeError
   */
  def registerAfterDeleteAccount(fn: AfterHookFunction[Unit, Unit]): Unit

  /**
   * Register before Hook for RPC authenticateApple function.
   *
   * @param fn - The function to execute before authenticateApple.
   * @throws TypeError
   */
  def registerBeforeAuthenticateApple(fn: BeforeHookFunction[AuthenticateAppleRequest]): Unit

  /**
   * Register After Hook for RPC authenticateApple function.
   *
   * @param fn - The function to execute after authenticateApple.
   * @throws TypeError
   */
  def registerAfterAuthenticateApple(fn: AfterHookFunction[Session, AuthenticateAppleRequest]): Unit

  /**
   * Register before Hook for RPC AuthenticateCustom function.
   *
   * @param fn - The function to execute before AuthenticateCustom.
   * @throws TypeError
   */
  def registerBeforeAuthenticateCustom(fn: BeforeHookFunction[AuthenticateCustomRequest]): Unit

  /**
   * Register after Hook for RPC AuthenticateCustom function.
   *
   * @param fn - The function to execute after AuthenticateCustom.
   * @throws TypeError
   */
  def registerAfterAuthenticateCustom(fn: AfterHookFunction[Session, AuthenticateCustomRequest]): Unit

  /**
   * Register before Hook for RPC AuthenticateDevice function.
   *
   * @param fn - The function to execute before AuthenticateDevice.
   * @throws TypeError
   */
  def registerBeforeAuthenticateDevice(fn: BeforeHookFunction[AuthenticateDeviceRequest]): Unit

  /**
   * Register after Hook for RPC AuthenticateDevice function.
   *
   * @param fn - The function to execute after AuthenticateDevice.
   * @throws TypeError
   */
  def registerAfterAuthenticateDevice(fn: AfterHookFunction[Session, AuthenticateDeviceRequest]): Unit

  /**
   * Register before Hook for RPC AuthenticateEmail function.
   *
   * @param fn - The function to execute before AuthenticateEmail.
   * @throws TypeError
   */
  def registerBeforeAuthenticateEmail(fn: BeforeHookFunction[AuthenticateEmailRequest]): Unit

  /**
   * Register after Hook for RPC AuthenticateEmail function.
   *
   * @param fn - The function to execute after AuthenticateEmail.
   * @throws TypeError
   */
  def registerAfterAuthenticateEmail(fn: AfterHookFunction[Session, AuthenticateEmailRequest]): Unit

  /**
   * Register before Hook for RPC AuthenticateFacebook function.
   *
   * @param fn - The function to execute before AuthenticateFacebook.
   * @throws TypeError
   */
  def registerBeforeAuthenticateFacebook(fn: BeforeHookFunction[AuthenticateFacebookRequest]): Unit

  /**
   * Register after Hook for RPC AuthenticateFacebook function.
   *
   * @param fn - The function to execute after AuthenticateFacebook.
   * @throws TypeError
   */
  def registerAfterAuthenticateFacebook(fn: AfterHookFunction[Session, AuthenticateFacebookRequest]): Unit

  /**
   * Register before Hook for RPC AuthenticateFacebookInstantGame function.
   *
   * @param fn - The function to execute before AuthenticateFacebookInstantGame.
   * @throws TypeError
   */
  def registerBeforeAuthenticateFacebookInstantGame(fn: BeforeHookFunction[AuthenticateFacebookInstantGameRequest]): Unit

  /**
   * Register after Hook for RPC AuthenticateFacebookInstantGame function.
   *
   * @param fn - The function to execute after AuthenticateFacebookInstantGame.
   * @throws TypeError
   */
  def registerAfterAuthenticateFacebookInstantGame(fn: AfterHookFunction[Session, AuthenticateFacebookInstantGameRequest]): Unit

  /**
   * Register before Hook for RPC AuthenticateGameCenter function.
   *
   * @param fn - The function to execute before AuthenticateGameCenter.
   * @throws TypeError
   */
  def registerBeforeAuthenticateGameCenter(fn: BeforeHookFunction[AuthenticateGameCenterRequest]): Unit

  /**
   * Register after Hook for RPC AuthenticateGameCenter function.
   *
   * @param fn - The function to execute after AuthenticateGameCenter.
   * @throws TypeError
   */
  def registerAfterAuthenticateGameCenter(fn: AfterHookFunction[Session, AuthenticateGameCenterRequest]): Unit

  /**
   * Register before Hook for RPC AuthenticateGoogle function.
   *
   * @param fn - The function to execute before AuthenticateGoogle.
   * @throws TypeError
   */
  def registerBeforeAuthenticateGoogle(fn: BeforeHookFunction[AuthenticateGoogleRequest]): Unit

  /**
   * Register after Hook for RPC AuthenticateGoogle function.
   *
   * @param fn - The function to execute after AuthenticateGoogle.
   * @throws TypeError
   */
  def registerAfterAuthenticateGoogle(fn: AfterHookFunction[Session, AuthenticateGoogleRequest]): Unit

  /**
   * Register before Hook for RPC AuthenticateSteam function.
   *
   * @param fn - The function to execute before AuthenticateSteam.
   * @throws TypeError
   */
  def registerBeforeAuthenticateSteam(fn: BeforeHookFunction[AuthenticateSteamRequest]): Unit

  /**
   * Register after Hook for RPC AuthenticateSteam function.
   *
   * @param fn - The function to execute after AuthenticateSteam.
   * @throws TypeError
   */
  def registerAfterAuthenticateSteam(fn: AfterHookFunction[Session, AuthenticateSteamRequest]): Unit

  /**
   * Register before Hook for RPC ChannelMessages function.
   *
   * @param fn - The function to execute before ChannelMessages.
   * @throws TypeError
   */
  def registerBeforeListChannelMessages(fn: BeforeHookFunction[ListChannelMessagesRequest]): Unit

  /**
   * Register after Hook for RPC ChannelMessages function.
   *
   * @param fn - The function to execute after ChannelMessages.
   * @throws TypeError
   */
  def registerAfterListChannelMessages(fn: AfterHookFunction[ChannelMessageList, ListChannelMessagesRequest]): Unit

  /**
   * Register before Hook for RPC BeforeListFriends function.
   *
   * @param fn - The function to execute before BeforeListFriends.
   * @throws TypeError
   */
  def registerBeforeListFriends(fn: BeforeHookFunction[ListFriendsRequest]): Unit

  /**
   * Register after Hook for RPC BeforeListFriends function.
   *
   * @param fn - The function to execute after BeforeListFriends.
   * @throws TypeError
   */
  def registerAfterListFriends(fn: AfterHookFunction[FriendList, ListFriendsRequest]): Unit

  /**
   * Register before Hook for RPC AddFriends function.
   *
   * @param fn - The function to execute before AddFriends.
   * @throws TypeError
   */
  def registerBeforeAddFriends(fn: BeforeHookFunction[AddFriendsRequest]): Unit

  /**
   * Register after Hook for RPC AddFriends function.
   *
   * @param fn - The function to execute after AddFriends.
   * @throws TypeError
   */
  def registerAfterAddFriends(fn: AfterHookFunction[Unit, AddFriendsRequest]): Unit

  /**
   * Register before Hook for RPC DeleteFriends function.
   *
   * @param fn - The function to execute before DeleteFriends.
   * @throws TypeError
   */
  def registerBeforeDeleteFriends(fn: BeforeHookFunction[DeleteFriendsRequest]): Unit

  /**
   * Register after Hook for RPC DeleteFriends function.
   *
   * @param fn - The function to execute after DeleteFriends.
   * @throws TypeError
   */
  def registerAfterDeleteFriends(fn: AfterHookFunction[Unit, DeleteFriendsRequest]): Unit

  /**
   * Register before Hook for RPC BlockFriends function.
   *
   * @param fn - The function to execute before BlockFriends.
   * @throws TypeError
   */
  def registerBeforeBlockFriends(fn: BeforeHookFunction[BlockFriendsRequest]): Unit

  /**
   * Register after Hook for RPC BlockFriends function.
   *
   * @param fn - The function to execute after BlockFriends.
   * @throws TypeError
   */
  def registerAfterBlockFriends(fn: AfterHookFunction[Unit, BlockFriendsRequest]): Unit

  /**
   * Register before Hook for RPC ImportFacebookFriends function.
   *
   * @param fn - The function to execute before ImportFacebookFriends.
   * @throws TypeError
   */
  def registerBeforeImportFacebookFriends(fn: BeforeHookFunction[ImportFacebookFriendsRequest]): Unit

  /**
   * Register after Hook for RPC ImportFacebookFriends function.
   *
   * @param fn - The function to execute after ImportFacebookFriends.
   * @throws TypeError
   */
  def registerAfterImportFacebookFriends(fn: AfterHookFunction[Unit, ImportFacebookFriendsRequest]): Unit

  /**
   * Register before Hook for RPC ImportSteamFriends function.
   *
   * @param fn - The function to execute before ImportSteamFriends.
   * @throws TypeError
   */
  def registerBeforeImportSteamFriends(fn: BeforeHookFunction[ImportSteamFriendsRequest]): Unit

  /**
   * Register after Hook for RPC ImportSteamFriends function.
   *
   * @param fn - The function to execute after ImportSteamFriends.
   * @throws TypeError
   */
  def registerAfterImportSteamFriends(fn: AfterHookFunction[Unit, ImportSteamFriendsRequest]): Unit

  /**
   * Register before Hook for RPC CreateGroup function.
   *
   * @param fn - The function to execute before CreateGroup.
   * @throws TypeError
   */
  def registerBeforeCreateGroup(fn: BeforeHookFunction[CreateGroupRequest]): Unit

  /**
   * Register after Hook for RPC CreateGroup function.
   *
   * @param fn - The function to execute after CreateGroup.
   * @throws TypeError
   */
  def registerAfterCreateGroup(fn: AfterHookFunction[Group, CreateGroupRequest]): Unit

  /**
   * Register before Hook for RPC UpdateGroup function.
   *
   * @param fn - The function to execute before UpdateGroup.
   * @throws TypeError
   */
  def registerBeforeUpdateGroup(fn: BeforeHookFunction[UpdateGroupRequest]): Unit

  /**
   * Register after Hook for RPC UpdateGroup function.
   *
   * @param fn - The function to execute after UpdateGroup.
   * @throws TypeError
   */
  def registerAfterUpdateGroup(fn: AfterHookFunction[Unit, UpdateGroupRequest]): Unit

  /**
   * Register before Hook for RPC DeleteGroup function.
   *
   * @param fn - The function to execute before DeleteGroup.
   * @throws TypeError
   */
  def registerBeforeDeleteGroup(fn: BeforeHookFunction[DeleteGroupRequest]): Unit

  /**
   * Register after Hook for RPC DeleteGroup function.
   *
   * @param fn - The function to execute after DeleteGroup.
   * @throws TypeError
   */
  def registerAfterDeleteGroup(fn: AfterHookFunction[Unit, DeleteGroupRequest]): Unit

  /**
   * Register before Hook for RPC JoinGroup function.
   *
   * @param fn - The function to execute before JoinGroup.
   * @throws TypeError
   */
  def registerBeforeJoinGroup(fn: BeforeHookFunction[JoinGroupRequest]): Unit

  /**
   * Register after Hook for RPC JoinGroup function.
   *
   * @param fn - The function to execute after JoinGroup.
   * @throws TypeError
   */
  def registerAfterJoinGroup(fn: AfterHookFunction[Unit, JoinGroupRequest]): Unit

  /**
   * Register before Hook for RPC LeaveGroup function.
   *
   * @param fn - The function to execute before LeaveGroup.
   * @throws TypeError
   */
  def registerBeforeLeaveGroup(fn: BeforeHookFunction[LeaveGroupRequest]): Unit

  /**
   * Register after Hook for RPC LeaveGroup function.
   *
   * @param fn - The function to execute after LeaveGroup.
   * @throws TypeError
   */
  def registerAfterLeaveGroup(fn: AfterHookFunction[Unit, LeaveGroupRequest]): Unit

  /**
   * Register before Hook for RPC AddGroupUsers function.
   *
   * @param fn - The function to execute before AddGroupUsers.
   * @throws TypeError
   */
  def registerBeforeAddGroupUsers(fn: BeforeHookFunction[AddGroupUsersRequest]): Unit

  /**
   * Register after Hook for RPC AddGroupUsers function.
   *
   * @param fn - The function to execute after AddGroupUsers.
   * @throws TypeError
   */
  def registerAfterAddGroupUsers(fn: AfterHookFunction[Unit, AddGroupUsersRequest]): Unit

  /**
   * Register before Hook for RPC BanGroupUsers function.
   *
   * @param fn - The function to execute before BanGroupUsers.
   * @throws TypeError
   */
  def registerBeforeBanGroupUsers(fn: BeforeHookFunction[BanGroupUsersRequest]): Unit

  /**
   * Register after Hook for RPC BanGroupUsers function.
   *
   * @param fn - The function to execute after BanGroupUsers.
   * @throws TypeError
   */
  def registerAfterBanGroupUsers(fn: AfterHookFunction[Unit, BanGroupUsersRequest]): Unit

  /**
   * Register before Hook for RPC KickGroupUsers function.
   *
   * @param fn - The function to execute before KickGroupUsers.
   * @throws TypeError
   */
  def registerBeforeKickGroupUsers(fn: BeforeHookFunction[KickGroupUsersRequest]): Unit

  /**
   * Register after Hook for RPC KickGroupUsers function.
   *
   * @param fn - The function to execute after KickGroupUsers.
   * @throws TypeError
   */
  def registerAfterKickGroupUsers(fn: AfterHookFunction[Unit, KickGroupUsersRequest]): Unit

  /**
   * Register before Hook for RPC PromoteGroupUsers function.
   *
   * @param fn - The function to execute before PromoteGroupUsers.
   * @throws TypeError
   */
  def registerBeforePromoteGroupUsers(fn: BeforeHookFunction[PromoteGroupUsersRequest]): Unit

  /**
   * Register after Hook for RPC PromoteGroupUsers function.
   *
   * @param fn - The function to execute after PromoteGroupUsers.
   * @throws TypeError
   */
  def registerAfterPromoteGroupUsers(fn: AfterHookFunction[Unit, PromoteGroupUsersRequest]): Unit

  /**
   * Register before Hook for RPC DemoteGroupUsers function.
   *
   * @param fn - The function to execute before DemoteGroupUsers.
   * @throws TypeError
   */
  def registerBeforeDemoteGroupUsers(fn: BeforeHookFunction[DemoteGroupUsersRequest]): Unit

  /**
   * Register after Hook for RPC DemoteGroupUsers function.
   *
   * @param fn - The function to execute after DemoteGroupUsers.
   * @throws TypeError
   */
  def registerAfterDemoteGroupUsers(fn: AfterHookFunction[Unit, DemoteGroupUsersRequest]): Unit

  /**
   * Register before Hook for RPC ListGroupUsers function.
   *
   * @param fn - The function to execute before ListGroupUsers.
   * @throws TypeError
   */
  def registerBeforeListGroupUsers(fn: BeforeHookFunction[ListGroupUsersRequest]): Unit

  /**
   * Register after Hook for RPC ListGroupUsers function.
   *
   * @param fn - The function to execute after ListGroupUsers.
   * @throws TypeError
   */
  def registerAfterListGroupUsers(fn: AfterHookFunction[GroupUserList, ListGroupUsersRequest]): Unit

  /**
   * Register before Hook for RPC ListUserGroups function.
   *
   * @param fn - The function to execute before ListUserGroups.
   * @throws TypeError
   */
  def registerBeforeListUserGroups(fn: BeforeHookFunction[ListUserGroupsRequest]): Unit

  /**
   * Register after Hook for RPC ListUserGroups function.
   *
   * @param fn - The function to execute after ListUserGroups.
   * @throws TypeError
   */
  def registerAfterListUserGroups(fn: AfterHookFunction[UserGroupList, ListUserGroupsRequest]): Unit

  /**
   * Register before Hook for RPC ListGroups function.
   *
   * @param fn - The function to execute before ListGroups.
   * @throws TypeError
   */
  def registerBeforeListGroups(fn: BeforeHookFunction[ListGroupsRequest]): Unit

  /**
   * Register after Hook for RPC ListGroups function.
   *
   * @param fn - The function to execute after ListGroups.
   * @throws TypeError
   */
  def registerAfterListGroups(fn: AfterHookFunction[GroupList, ListGroupsRequest]): Unit

  /**
   * Register before Hook for RPC DeleteLeaderboardRecord function.
   *
   * @param fn - The function to execute before DeleteLeaderboardRecord.
   * @throws TypeError
   */
  def registerBeforeDeleteLeaderboardRecord(fn: BeforeHookFunction[DeleteLeaderboardRecordRequest]): Unit

  /**
   * Register after Hook for RPC DeleteLeaderboardRecord function.
   *
   * @param fn - The function to execute after DeleteLeaderboardRecord.
   * @throws TypeError
   */
  def registerAfterDeleteLeaderboardRecord(fn: AfterHookFunction[Unit, DeleteLeaderboardRecordRequest]): Unit

  /**
   * Register before Hook for RPC ListLeaderboardRecords function.
   *
   * @param fn - The function to execute before ListLeaderboardRecords.
   * @throws TypeError
   */
  def registerBeforeListLeaderboardRecords(fn: BeforeHookFunction[ListLeaderboardRecordsRequest]): Unit

  /**
   * Register after Hook for RPC ListLeaderboardRecords function.
   *
   * @param fn - The function to execute after ListLeaderboardRecords.
   * @throws TypeError
   */
  def registerAfterListLeaderboardRecords(fn: AfterHookFunction[LeaderboardRecordList, ListLeaderboardRecordsRequest]): Unit

  /**
   * Register before Hook for RPC WriteLeaderboardRecord function.
   *
   * @param fn - The function to execute before WriteLeaderboardRecord.
   * @throws TypeError
   */
  def registerBeforeWriteLeaderboardRecord(fn: BeforeHookFunction[WriteLeaderboardRecordRequest]): Unit

  /**
   * Register after Hook for RPC WriteLeaderboardRecord function.
   *
   * @param fn - The function to execute after WriteLeaderboardRecord.
   * @throws TypeError
   */
  def registerAfterWriteLeaderboardRecord(fn: AfterHookFunction[LeaderboardRecord, WriteLeaderboardRecordRequest]): Unit

  /**
   * Register before Hook for RPC ListLeaderboardRecordsAroundOwner function.
   *
   * @param fn - The function to execute before ListLeaderboardRecordsAroundOwner.
   * @throws TypeError
   */
  def registerBeforeListLeaderboardRecordsAroundOwner(fn: BeforeHookFunction[ListLeaderboardRecordsAroundOwnerRequest]): Unit

  /**
   * Register after Hook for RPC ListLeaderboardRecordsAroundOwner function.
   *
   * @param fn - The function to execute after ListLeaderboardRecordsAroundOwner.
   * @throws TypeError
   */
  def registerAfterListLeaderboardRecordsAroundOwner(fn: AfterHookFunction[LeaderboardRecordList, ListLeaderboardRecordsAroundOwnerRequest]): Unit

  /**
   * Register before Hook for RPC LinkApple function.
   *
   * @param fn - The function to execute before LinkApple.
   * @throws TypeError
   */
  def registerBeforeLinkApple(fn: BeforeHookFunction[AccountApple]): Unit

  /**
   * Register after Hook for RPC LinkApple function.
   *
   * @param fn - The function to execute after LinkApple.
   * @throws TypeError
   */
  def registerAfterLinkApple(fn: AfterHookFunction[Unit, AccountApple]): Unit

  /**
   * Register before Hook for RPC LinkCustom function.
   *
   * @param fn - The function to execute before LinkCustom.
   * @throws TypeError
   */
  def registerBeforeLinkCustom(fn: BeforeHookFunction[AccountCustom]): Unit

  /**
   * Register after Hook for RPC LinkCustom function.
   *
   * @param fn - The function to execute after LinkCustom.
   * @throws TypeError
   */
  def registerAfterLinkCustom(fn: AfterHookFunction[Unit, AccountCustom]): Unit

  /**
   * Register before Hook for RPC LinkDevice function.
   *
   * @param fn - The function to execute before LinkDevice.
   * @throws TypeError
   */
  def registerBeforeLinkDevice(fn: BeforeHookFunction[AccountDevice]): Unit

  /**
   * Register after Hook for RPC LinkDevice function.
   *
   * @param fn - The function to execute after LinkDevice.
   * @throws TypeError
   */
  def registerAfterLinkDevice(fn: AfterHookFunction[Unit, AccountDevice]): Unit

  /**
   * Register before Hook for RPC LinkEmail function.
   *
   * @param fn - The function to execute before LinkEmail.
   * @throws TypeError
   */
  def registerBeforeLinkEmail(fn: BeforeHookFunction[AccountEmail]): Unit

  /**
   * Register after Hook for RPC LinkEmail function.
   *
   * @param fn - The function to execute after LinkEmail.
   * @throws TypeError
   */
  def registerAfterLinkEmail(fn: AfterHookFunction[Unit, AccountEmail]): Unit

  /**
   * Register before Hook for RPC LinkFacebook function.
   *
   * @param fn - The function to execute before LinkFacebook.
   * @throws TypeError
   */
  def registerBeforeLinkFacebook(fn: BeforeHookFunction[LinkFacebookRequest]): Unit

  /**
   * Register after Hook for RPC LinkFacebook function.
   *
   * @param fn - The function to execute after LinkFacebook.
   * @throws TypeError
   */
  def registerAfterLinkFacebook(fn: AfterHookFunction[Unit, LinkFacebookRequest]): Unit

  /**
   * Register before Hook for RPC LinkFacebookInstantGame function.
   *
   * @param fn - The function to execute before LinkFacebookInstantGame.
   * @throws TypeError
   */
  def registerBeforeLinkFacebookInstantGame(fn: BeforeHookFunction[AccountFacebookInstantGame]): Unit

  /**
   * Register after Hook for RPC LinkFacebookInstantGame function.
   *
   * @param fn - The function to execute after LinkFacebookInstantGame.
   * @throws TypeError
   */
  def registerAfterLinkFacebookInstantGame(fn: AfterHookFunction[Unit, AccountFacebookInstantGame]): Unit

  /**
   * Register before Hook for RPC LinkGameCenter function.
   *
   * @param fn - The function to execute before LinkGameCenter.
   * @throws TypeError
   */
  def registerBeforeLinkGameCenter(fn: BeforeHookFunction[AccountGameCenter]): Unit

  /**
   * Register after Hook for RPC LinkGameCenter function.
   *
   * @param fn - The function to execute after LinkGameCenter.
   * @throws TypeError
   */
  def registerAfterLinkGameCenter(fn: AfterHookFunction[Unit, AccountGameCenter]): Unit

  /**
   * Register before Hook for RPC LinkGoogle function.
   *
   * @param fn - The function to execute before LinkGoogle.
   * @throws TypeError
   */
  def registerBeforeLinkGoogle(fn: BeforeHookFunction[AccountGoogle]): Unit

  /**
   * Register after Hook for RPC LinkGoogle function.
   *
   * @param fn - The function to execute after LinkGoogle.
   * @throws TypeError
   */
  def registerAfterLinkGoogle(fn: AfterHookFunction[Unit, AccountGoogle]): Unit

  /**
   * Register before Hook for RPC LinkSteam function.
   *
   * @param fn - The function to execute before LinkSteam.
   * @throws TypeError
   */
  def registerBeforeLinkSteam(fn: BeforeHookFunction[LinkSteamRequest]): Unit

  /**
   * Register after Hook for RPC LinkSteam function.
   *
   * @param fn - The function to execute after LinkSteam.
   * @throws TypeError
   */
  def registerAfterLinkSteam(fn: AfterHookFunction[Unit, LinkSteamRequest]): Unit

  /**
   * Register before Hook for RPC ListMatches function.
   *
   * @param fn - The function to execute before ListMatches.
   * @throws TypeError
   */
  def registerBeforeListMatches(fn: BeforeHookFunction[ListMatchesRequest]): Unit

  /**
   * Register after Hook for RPC ListMatches function.
   *
   * @param fn - The function to execute after ListMatches.
   * @throws TypeError
   */
  def registerAfterListMatches(fn: AfterHookFunction[MatchList, ListMatchesRequest]): Unit

  /**
   * Register before Hook for RPC ListNotifications function.
   *
   * @param fn - The function to execute before ListNotifications.
   * @throws TypeError
   */
  def registerBeforeListNotifications(fn: BeforeHookFunction[ListNotificationsRequest]): Unit

  /**
   * Register after Hook for RPC ListNotifications function.
   *
   * @param fn - The function to execute after ListNotifications.
   * @throws TypeError
   */
  def registerAfterListNotifications(fn: AfterHookFunction[NotificationList, ListNotificationsRequest]): Unit

  /**
   * Register before Hook for RPC DeleteNotifications function.
   *
   * @param fn - The function to execute before DeleteNotifications.
   * @throws TypeError
   */
  def registerBeforeDeleteNotifications(fn: BeforeHookFunction[DeleteNotificationsRequest]): Unit

  /**
   * Register after Hook for RPC DeleteNotifications function.
   *
   * @param fn - The function to execute after DeleteNotifications.
   * @throws TypeError
   */
  def registerAfterDeleteNotifications(fn: AfterHookFunction[Unit, DeleteNotificationsRequest]): Unit

  /**
   * Register before Hook for RPC ListStorageObjects function.
   *
   * @param fn - The function to execute before ListStorageObjects.
   * @throws TypeError
   */
  def registerBeforeListStorageObjects(fn: BeforeHookFunction[ListStorageObjectsRequest]): Unit

  /**
   * Register after Hook for RPC ListStorageObjects function.
   *
   * @param fn - The function to execute after ListStorageObjects.
   * @throws TypeError
   */
  def registerAfterListStorageObjects(fn: AfterHookFunction[StorageObjectList, ListStorageObjectsRequest]): Unit

  /**
   * Register before Hook for RPC ReadStorageObjects function.
   *
   * @param fn - The function to execute before ReadStorageObjects.
   * @throws TypeError
   */
  def registerBeforeReadStorageObjects(fn: BeforeHookFunction[ReadStorageObjectsRequest]): Unit

  /**
   * Register after Hook for RPC ReadStorageObjects function.
   *
   * @param fn - The function to execute after ReadStorageObjects.
   * @throws TypeError
   */
  def registerAfterReadStorageObjects(fn: AfterHookFunction[StorageObjects, ReadStorageObjectsRequest]): Unit

  /**
   * Register before Hook for RPC WriteStorageObjects function.
   *
   * @param fn - The function to execute before WriteStorageObjects.
   * @throws TypeError
   */
  def registerBeforeWriteStorageObjects(fn: BeforeHookFunction[WriteStorageObjectsRequest]): Unit

  /**
   * Register after Hook for RPC WriteStorageObjects function.
   *
   * @param fn - The function to execute after WriteStorageObjects.
   * @throws TypeError
   */
  def registerAfterWriteStorageObjects(fn: AfterHookFunction[StorageObjectAcks, WriteStorageObjectsRequest]): Unit

  /**
   * Register before Hook for RPC DeleteStorageObjects function.
   *
   * @param fn - The function to execute before DeleteStorageObjects.
   * @throws TypeError
   */
  def registerBeforeDeleteStorageObjects(fn: BeforeHookFunction[DeleteStorageObjectsRequest]): Unit

  /**
   * Register after Hook for RPC DeleteStorageObjects function.
   *
   * @param fn - The function to execute after DeleteStorageObjects.
   * @throws TypeError
   */
  def registerAfterDeleteStorageObjects(fn: AfterHookFunction[Unit, DeleteStorageObjectsRequest]): Unit

  /**
   * Register before Hook for RPC JoinTournament function.
   *
   * @param fn - The function to execute before JoinTournament.
   * @throws TypeError
   */
  def registerBeforeJoinTournament(fn: BeforeHookFunction[JoinTournamentRequest]): Unit

  /**
   * Register after Hook for RPC JoinTournament function.
   *
   * @param fn - The function to execute after JoinTournament.
   * @throws TypeError
   */
  def registerAfterJoinTournament(fn: AfterHookFunction[Unit, JoinTournamentRequest]): Unit

  /**
   * Register before Hook for RPC ListTournamentRecords function.
   *
   * @param fn - The function to execute before ListTournamentRecords.
   * @throws TypeError
   */
  def registerBeforeListTournamentRecords(fn: BeforeHookFunction[ListTournamentRecordsRequest]): Unit

  /**
   * Register after Hook for RPC ListTournamentRecords function.
   *
   * @param fn - The function to execute after ListTournamentRecords.
   * @throws TypeError
   */
  def registerAfterListTournamentRecords(fn: AfterHookFunction[TournamentRecordList, ListTournamentRecordsRequest]): Unit

  /**
   * Register before Hook for RPC ListTournaments function.
   *
   * @param fn - The function to execute before ListTournaments.
   * @throws TypeError
   */
  def registerBeforeListTournaments(fn: BeforeHookFunction[ListTournamentsRequest]): Unit

  /**
   * Register after Hook for RPC ListTournaments function.
   *
   * @param fn - The function to execute after ListTournaments.
   * @throws TypeError
   */
  def registerAfterListTournaments(fn: AfterHookFunction[TournamentList, ListTournamentsRequest]): Unit

  /**
   * Register before Hook for RPC WriteTournamentRecord function.
   *
   * @param fn - The function to execute before WriteTournamentRecord.
   * @throws TypeError
   */
  def registerBeforeWriteTournamentRecord(fn: BeforeHookFunction[WriteTournamentRecordRequest]): Unit

  /**
   * Register after Hook for RPC WriteTournamentRecord function.
   *
   * @param fn - The function to execute after WriteTournamentRecord.
   * @throws TypeError
   */
  def registerAfterWriteTournamentRecord(fn: AfterHookFunction[LeaderboardRecord, WriteTournamentRecordRequest]): Unit

  /**
   * Register before Hook for RPC ListTournamentRecordsAroundOwner function.
   *
   * @param fn - The function to execute before ListTournamentRecordsAroundOwner.
   * @throws TypeError
   */
  def registerBeforeListTournamentRecordsAroundOwner(fn: BeforeHookFunction[ListTournamentRecordsAroundOwnerRequest]): Unit

  /**
   * Register after Hook for RPC ListTournamentRecordsAroundOwner function.
   *
   * @param fn - The function to execute after ListTournamentRecordsAroundOwner.
   * @throws TypeError
   */
  def registerAfterListTournamentRecordsAroundOwner(fn: AfterHookFunction[LeaderboardRecordList, ListTournamentRecordsAroundOwnerRequest]): Unit

  /**
   * Register before Hook for RPC UnlinkApple function.
   *
   * @param fn - The function to execute before UnlinkApple.
   * @throws TypeError
   */
  def registerBeforeUnlinkApple(fn: BeforeHookFunction[AccountApple]): Unit

  /**
   * Register after Hook for RPC UnlinkApple function.
   *
   * @param fn - The function to execute after UnlinkApple.
   * @throws TypeError
   */
  def registerAfterUnlinkApple(fn: AfterHookFunction[Unit, AccountApple]): Unit

  /**
   * Register before Hook for RPC UnlinkCustom function.
   *
   * @param fn - The function to execute before UnlinkCustom.
   * @throws TypeError
   */
  def registerBeforeUnlinkCustom(fn: BeforeHookFunction[AccountCustom]): Unit

  /**
   * Register after Hook for RPC UnlinkCustom function.
   *
   * @param fn - The function to execute after UnlinkCustom.
   * @throws TypeError
   */
  def registerAfterUnlinkCustom(fn: AfterHookFunction[Unit, AccountCustom]): Unit

  /**
   * Register before Hook for RPC UnlinkDevice function.
   *
   * @param fn - The function to execute before UnlinkDevice.
   * @throws TypeError
   */
  def registerBeforeUnlinkDevice(fn: BeforeHookFunction[AccountDevice]): Unit

  /**
   * Register after Hook for RPC UnlinkDevice function.
   *
   * @param fn - The function to execute after UnlinkDevice.
   * @throws TypeError
   */
  def registerAfterUnlinkDevice(fn: AfterHookFunction[Unit, AccountDevice]): Unit

  /**
   * Register before Hook for RPC UnlinkEmail function.
   *
   * @param fn - The function to execute before UnlinkEmail.
   * @throws TypeError
   */
  def registerBeforeUnlinkEmail(fn: BeforeHookFunction[AccountEmail]): Unit

  /**
   * Register after Hook for RPC UnlinkEmail function.
   *
   * @param fn - The function to execute after UnlinkEmail.
   * @throws TypeError
   */
  def registerAfterUnlinkEmail(fn: AfterHookFunction[Unit, AccountEmail]): Unit

  /**
   * Register before Hook for RPC UnlinkFacebook function.
   *
   * @param fn - The function to execute before UnlinkFacebook.
   * @throws TypeError
   */
  def registerBeforeUnlinkFacebook(fn: BeforeHookFunction[AccountFacebookInstantGame]): Unit

  /**
   * Register after Hook for RPC UnlinkFacebook function.
   *
   * @param fn - The function to execute after UnlinkFacebook.
   * @throws TypeError
   */
  def registerAfterUnlinkFacebook(fn: AfterHookFunction[Unit, AccountFacebook]): Unit

  /**
   * Register before Hook for RPC UnlinkFacebookInstantGame function.
   *
   * @param fn - The function to execute before UnlinkFacebookInstantGame.
   * @throws TypeError
   */
  def registerBeforeUnlinkFacebookInstantGame(fn: BeforeHookFunction[AccountFacebook]): Unit

  /**
   * Register after Hook for RPC UnlinkFacebookInstantGame function.
   *
   * @param fn - The function to execute after UnlinkFacebookInstantGame.
   * @throws TypeError
   */
  def registerAfterUnlinkFacebookInstantGame(fn: AfterHookFunction[Unit, AccountFacebookInstantGame]): Unit

  /**
   * Register before Hook for RPC UnlinkGameCenter function.
   *
   * @param fn - The function to execute before UnlinkGameCenter.
   * @throws TypeError
   */
  def registerBeforeUnlinkGameCenter(fn: BeforeHookFunction[AccountGameCenter]): Unit

  /**
   * Register after Hook for RPC UnlinkGameCenter function.
   *
   * @param fn - The function to execute after UnlinkGameCenter.
   * @throws TypeError
   */
  def registerAfterUnlinkGameCenter(fn: AfterHookFunction[Unit, AccountGameCenter]): Unit

  /**
   * Register before Hook for RPC UnlinkGoogle function.
   *
   * @param fn - The function to execute before UnlinkGoogle.
   * @throws TypeError
   */
  def registerBeforeUnlinkGoogle(fn: BeforeHookFunction[AccountGoogle]): Unit

  /**
   * Register after Hook for RPC UnlinkGoogle function.
   *
   * @param fn - The function to execute after UnlinkGoogle.
   * @throws TypeError
   */
  def registerAfterUnlinkGoogle(fn: AfterHookFunction[Unit, AccountGoogle]): Unit

  /**
   * Register before Hook for RPC UnlinkSteam function.
   *
   * @param fn - The function to execute before UnlinkSteam.
   * @throws TypeError
   */
  def registerBeforeUnlinkSteam(fn: BeforeHookFunction[AccountSteam]): Unit

  /**
   * Register after Hook for RPC UnlinkSteam function.
   *
   * @param fn - The function to execute after UnlinkSteam.
   * @throws TypeError
   */
  def registerAfterUnlinkSteam(fn: AfterHookFunction[Unit, AccountSteam]): Unit

  /**
   * Register before Hook for RPC GetUsers function.
   *
   * @param fn - The function to execute before GetUsers.
   * @throws TypeError
   */
  def registerBeforeGetUsers(fn: BeforeHookFunction[GetUsersRequest]): Unit

  /**
   * Register after Hook for RPC GetUsers function.
   *
   * @param fn - The function to execute after GetUsers.
   * @throws TypeError
   */
  def registerAfterGetUsers(fn: AfterHookFunction[Users, GetUsersRequest]): Unit

  /**
   * Register before Hook for RPC ValidatePurchaseApple function.
   *
   * @param fn - The function to execute before ValidatePurchaseApple.
   * @throws TypeError
   */
  def registerBeforeValidatePurchaseApple(fn: BeforeHookFunction[ValidatePurchaseAppleRequest]): Unit

  /**
   * Register after Hook for RPC ValidatePurchaseApple function.
   *
   * @param fn - The function to execute after ValidatePurchaseApple.
   * @throws TypeError
   */
  def registerAfterValidatePurchaseApple(fn: AfterHookFunction[ValidatePurchaseResponse, ValidatePurchaseAppleRequest]): Unit

  /**
   * Register before Hook for RPC ValidateSubscriptionApple function.
   *
   * @param fn - The function to execute before ValidatePurchaseApple.
   * @throws TypeError
   */
  def registerBeforeValidateSubscriptionApple(fn: BeforeHookFunction[ValidateSubscriptionAppleRequest]): Unit

  /**
   * Register after Hook for RPC ValidatePurchaseApple function.
   *
   * @param fn - The function to execute after ValidatePurchaseApple.
   * @throws TypeError
   */
  def registerAfterValidateSubscriptionApple(fn: AfterHookFunction[ValidateSubscriptionResponse, ValidateSubscriptionAppleRequest]): Unit

  /**
   * Register before Hook for RPC ValidatePurchaseGoogle function.
   *
   * @param fn - The function to execute before ValidatePurchaseGoogle.
   * @throws TypeError
   */
  def registerBeforeValidatePurchaseGoogle(fn: BeforeHookFunction[ValidatePurchaseGoogleRequest]): Unit

  /**
   * Register after Hook for RPC ValidatePurchaseGoogle function.
   *
   * @param fn - The function to execute after ValidatePurchaseGoogle.
   * @throws TypeError
   */
  def registerAfterValidatePurchaseGoogle(fn: AfterHookFunction[ValidatePurchaseResponse, ValidatePurchaseGoogleRequest]): Unit

  /**
   * Register before Hook for RPC ValidateSubscriptionGoogle function.
   *
   * @param fn - The function to execute before ValidateSubscriptionGoogle.
   * @throws TypeError
   */
  def registerBeforeValidateSubscriptionGoogle(fn: BeforeHookFunction[ValidateSubscriptionGoogleRequest]): Unit

  /**
   * Register after Hook for RPC ValidateSubscriptionGoogle function.
   *
   * @param fn - The function to execute after ValidateSubscriptionGoogle.
   * @throws TypeError
   */
  def registerAfterValidateSubscriptionGoogle(fn: AfterHookFunction[ValidateSubscriptionResponse, ValidateSubscriptionGoogleRequest]): Unit

  /**
   * Register before Hook for RPC ValidatePurchaseHuawei function.
   *
   * @param fn - The function to execute before ValidatePurchaseHuawei.
   * @throws TypeError
   */
  def registerBeforeValidatePurchaseHuawei(fn: BeforeHookFunction[ValidatePurchaseHuaweiRequest]): Unit

  /**
   * Register after Hook for RPC ValidatePurchaseHuawei function.
   *
   * @param fn - The function to execute after ValidatePurchaseHuawei.
   * @throws TypeError
   */
  def registerAfterValidatePurchaseHuawei(fn: AfterHookFunction[ValidatePurchaseResponse, ValidatePurchaseHuaweiRequest]): Unit

  /**
   * Register before Hook for RPC Event function.
   *
   * @param fn - The function to execute before Event.
   * @throws TypeError
   */
  def registerBeforeEvent(fn: BeforeHookFunction[Event]): Unit

  /**
   * Register after Hook for RPC Event function.
   *
   * @param fn - The function to execute after Event.
   * @throws TypeError
   */
  def registerAfterEvent(fn: AfterHookFunction[Unit, Event]): Unit

  /**
   * Register a match handler.
   *
   * @param name      - Identifier of the match handler.
   * @param functions - Object containing the match handler functions.
   */
  def registerMatch[State <: MatchState](name: String, functions: MatchHandler[State]): Unit

  /**
   * Register matchmaker matched handler.
   *
   * @param fn - The function to execute after a matchmaker match.
   */
  def registerMatchmakerMatched(fn: MatchmakerMatchedFunction): Unit

  /**
   * Register tournament end handler.
   *
   * @param fn - The function to execute after a tournament ends.
   */
  def registerTournamentEnd(fn: TournamentEndFunction): Unit

  /**
   * Register tournament reset handler.
   *
   * @param fn - The function to execute after a tournament resets.
   */
  def registerTournamentReset(fn: TournamentResetFunction): Unit

  /**
   * Register leaderboard reset handler.
   *
   * @param fn - The function to execute after a leaderboard resets.
   */
  def registerLeaderboardReset(fn: LeaderboardResetFunction): Unit

  /**
   * Register purchase notification Apple handler.
   *
   * @param fn - The function to execute after an Apple IAP Notification is received.
   */
  def registerPurchaseNotificationApple(fn: PurchaseNotificationAppleFunction): Unit

  /**
   * Register purchase notification Apple handler.
   *
   * @param fn - The function to execute after an Apple IAP Notification is received.
   */
  def registerSubscriptionNotificationApple(fn: SubscriptionNotificationAppleFunction): Unit

  /**
   * Register purchase notification Google handler.
   *
   * @param fn - The function to execute after a Google IAP Notification is received.
   */
  def registerPurchaseNotificationGoogle(fn: PurchaseNotificationGoogleFunction): Unit

  /**
   * Register purchase notification Google handler.
   *
   * @param fn - The function to execute after a Google IAP Notification is received.
   */
  def registerSubscriptionNotificationGoogle(fn: SubscriptionNotificationGoogleFunction): Unit
}

/**
 * A structured logger to output messages to the game server.
 */
trait Logger extends js.Object {
  /**
   * Log a message with optional formatted arguments at INFO level.
   *
   * @param format - A string with optional formatting placeholders.
   * @param args   - The placeholder arguments for the formatted string.
   * @return The formatted string logged to the server.
   */
  def info(format: String, args: js.Any*): String

  /**
   * Log a message with optional formatted arguments at WARN level.
   *
   * @param format - A string with optional formatting placeholders.
   * @param args   - The placeholder arguments for the formatted string.
   * @return The formatted string logged to the server.
   */
  def warn(format: String, args: js.Any*): String

  /**
   * Log a message with optional formatted arguments at ERROR level.
   *
   * @param format - A string with optional formatting placeholders.
   * @param args   - The placeholder arguments for the formatted string.
   * @return The formatted string logged to the server.
   */
  def error(format: String, args: js.Any*): String

  /**
   * Log a message with optional formatted arguments at DEBUG level.
   *
   * @param format - A string with optional formatting placeholders.
   * @param args   - The placeholder arguments for the formatted string.
   * @return The formatted string logged to the server.
   */
  def debug(format: String, args: js.Any*): String

  /**
   * A logger with the key/value pair added as the fields logged alongside the message.
   *
   * @param key   - The key name for the field.
   * @param value - The value for the field.
   * @return The modified logger with the new structured fields.
   */
  def withField(key: String, value: String): Logger

  /**
   * A new logger with the key/value pairs added as fields logged alongside the message.
   *
   * @param pairs - The pairs of key/value fields to add.
   * @return The modified logger with the new structured fields.
   */
  def withFields(pairs: js.Dictionary[String]): Logger

  /**
   * The fields associated with this logger.
   *
   * @return The map of fields in the logger.
   */
  def getFields(): js.Dictionary[String]
}

/**
 * Request method type
 */
type RequestMethod = "get" | "post" | "put" | "patch" | "head" | "delete"

/**
 * HTTP Response type
 */
trait HttpResponse extends js.Object {
  /**
   * Http Response status code.
   */
  val code: Number

  /**
   * Http Response headers.
   */
  val headers: js.Array[String]
  /**
   * Http Response body.
   */
  val body: String
}

/**
 * Object returned on successful user authentication
 */
trait AuthResult extends js.Object {
  /**
   * Authenticated User ID.
   */
  val userId: String

  /**
   * Authenticated Username.
   */
  val username: String

  /**
   * New user created
   */
  val created: Boolean
}

/**
 * Object returned on authentication token generation
 */
trait TokenGenerateResult extends js.Object {
  /**
   * Authentication token
   */
  val token: String

  /**
   * Token expire - Unix epoch
   */
  val exp: Number
}

/**
 * Account device object
 */
trait AccountDevice extends js.Object {
  val id: String
  val vars: js.UndefOr[SessionVars] = js.undefined
}

type Wallet = js.Dictionary[Number]

/**
 * Account object
 */
trait Account extends js.Object {
  val user: User
  val wallet: Wallet
  val email: String
  val devices: js.Array[AccountDevice]
  val customId: String
  val verifyTime: Number
  val disableTime: Number
}

/**
 * User object
 */
trait User extends js.Object {
  val userId: String
  val username: String
  val displayName: String
  val avatarUrl: String
  val langTag: String
  val location: String
  val timezone: String
  val appleId: String
  val facebookId: String
  val facebookInstantGameId: String
  val googleId: String
  val gamecenterId: String
  val steamId: String
  val online: Boolean
  val edgeCount: Number
  val createTime: Number
  val updateTime: Number
  val metadata: js.Dictionary[js.Any]
}

/**
 * User update account object
 */
trait UserUpdateAccount extends js.Object {
  val userId: String
  val username: js.UndefOr[String] = js.undefined
  val displayName: js.UndefOr[String] = js.undefined
  val avatarUrl: js.UndefOr[String] = js.undefined
  val langTag: js.UndefOr[String] = js.undefined
  val location: js.UndefOr[String] = js.undefined
  val timezone: js.UndefOr[String] = js.undefined
  val metadata: js.UndefOr[js.Dictionary[js.Any]] = js.undefined
}

/**
 * Stream object
 */
trait Stream extends js.Object {
  val mode: js.UndefOr[Number] = js.undefined
  val subject: js.UndefOr[String] = js.undefined
  val subcontext: js.UndefOr[String] = js.undefined
  val label: js.UndefOr[String] = js.undefined
}

/**
 * Presence object
 */
trait Presence extends js.Object {
  val userId: String
  val sessionId: String
  val username: String
  val node: String
  val status: js.UndefOr[String]
  val hidden: js.UndefOr[Boolean]
  val persistence: js.UndefOr[Boolean]
  val reason: js.UndefOr[PresenceReason.Type] = js.undefined
}

/**
 * Match Object
 */
trait Match extends js.Object {
  val matchId: String
  val authoritative: Boolean
  val size: Number
  val label: String
}

/**
 * Notification Object
 */
trait Notification extends js.Object {
  val code: Number
  val content: js.Dictionary[js.Any]
  val persistent: Boolean
  val senderId: String
  val subject: String
  val userId: String
}

trait NotificationDeleteRequest extends js.Object {
  val notificationId: String
  val userId: String
}

trait NotificationRequest extends js.Object {
  val code: Number
  val content: js.Dictionary[js.Any]
  val persistent: js.UndefOr[Boolean] = js.undefined
  val senderId: js.UndefOr[String | Null] = js.undefined
  val subject: String
  val userId: String
}

trait NotificationList extends js.Object {
  val notifications: js.UndefOr[js.Array[Notification]] = js.undefined
  val cacheableCursor: js.UndefOr[String] = js.undefined
}

/**
 * Wallet Update
 */
trait WalletUpdate extends js.Object {
  val userId: String
  val changeset: js.Dictionary[Number]
  val metadata: js.UndefOr[js.Dictionary[js.Any]] = js.undefined
}

/**
 * Wallet Update Result
 */
trait WalletUpdateResult extends js.Object {
  // The user ID of the wallet.
  val userId: String
  // The wallet values after the update.
  val updated: js.Dictionary[Number]
  // The wallet value prior to the update.
  val previous: js.Dictionary[Number]
}

/**
 * Wallet Ledger Update Result
 */
trait WalletLedgerResult extends js.Object {
  val id: String
  val userId: String
  val createTime: Number
  val updateTime: Number
  val changeset: js.Dictionary[Number]
  val metadata: js.Dictionary[js.Any]
}

/**
 * Storage Object
 */
trait StorageObject extends js.Object {
  val key: String
  val collection: String
  val userId: String
  val version: String
  val permissionRead: ReadPermissionValues
  val permissionWrite: WritePermissionValues
  val createTime: Number
  val updateTime: Number
  val value: js.Dictionary[js.Any]
}

/**
 * Storage Read Request
 */
trait StorageReadRequest extends js.Object {
  val key: String
  val collection: String
  val userId: String
}

/**
 * Storage Write Request
 */
trait StorageWriteRequest extends js.Object {
  val key: String
  val collection: String
  val userId: js.UndefOr[String] = js.undefined
  val value: js.Dictionary[js.Any]
  val version: js.UndefOr[String] = js.undefined
  val permissionRead: js.UndefOr[ReadPermissionValues] = js.undefined
  val permissionWrite: js.UndefOr[WritePermissionValues] = js.undefined
}

/**
 * Storage Write Ack
 */
trait StorageWriteAck extends js.Object {
  val key: String
  val collection: String
  val userId: String
  val version: String
}

/**
 * A list of Write Acks
 */
trait StorageObjectAcks extends js.Object {
  val acks: js.Array[StorageWriteAck]
}

/**
 * Storage Delete Request
 */
trait StorageDeleteRequest extends js.Object {
  val key: String
  val collection: String
  val userId: String
  val version: js.UndefOr[String] = js.undefined
}

/**
 * Leaderboard Entry
 */
trait Leaderboard extends js.Object {
  val id: String
  val title: String
  val description: String
  val category: Number
  val authoritative: Boolean
  val sortOrder: Number
  val operator: Operator.Type
  val prevReset: Number
  val nextReset: Number
  val metadata: js.Dictionary[js.Any]
  val createTime: Number
}

/**
 * Leaderboard Entry
 */
trait LeaderboardRecord extends js.Object {
  val leaderboardId: String
  val ownerId: String
  val username: String
  val score: Number
  val subscore: Number
  val numScore: Number
  val metadata: js.Dictionary[js.Any]
  val createTime: Number
  val updateTime: Number
  val expiryTime: Number
  val rank: Number
}

/**
 * Tournament Entry
 */
trait Tournament extends js.Object {
  val id: String
  val title: String
  val description: String
  val category: Number
  val sortOrder: Number
  val size: Number
  val maxSize: Number
  val maxNumScore: Number
  val duration: Number
  val startActive: Number
  val endActive: Number
  val canEnter: Boolean
  val nextReset: String
  val metadata: js.Dictionary[js.Any]
  val createTime: Number
  val startTime: Number
  val endTime: Number
}

/**
 * Group Entry
 */
trait Group extends js.Object {
  val id: String
  val creatorId: String
  val name: String
  val description: String
  val avatarUrl: String
  val langTag: String
  val open: Boolean
  val edgeCount: Number
  val maxCount: Number
  val createTime: Number
  val updateTime: Number
  val metadata: js.Dictionary[js.Any]
}

trait UserGroupList extends js.Object {
  val userGroups: js.UndefOr[js.Array[UserGroupListUserGroup]] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
}

trait UserGroupListUserGroup extends js.Object {
  val group: js.UndefOr[Group] = js.undefined
  val state: js.UndefOr[Number] = js.undefined
}

trait GroupList extends js.Object {
  val groups: js.UndefOr[js.Array[Group]] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
}

object SortOrder {
  type Type = "ascending" | "descending"
  val ASCENDING: Type = "ascending"
  val DESCENDING: Type = "descending"
}

object Operator {
  type Type = "best" | "set" | "increment"
  val BEST: Type = "best"
  val SET: Type = "set"
  val INCREMENTAL: Type = "increment"
}

object OverrideOperator {
  type Type = "best" | "set" | "increment" | "decrement"
  val BEST: Type = "best"
  val SET: Type = "set"
  val INCREMENTAL: Type = "increment"
  val DECREMENTAL: Type = "decrement"
}

/**
 * Envelope for realtime message hooks
 */
type Envelope = EnvelopeChannel | EnvelopeChannelJoin | EnvelopeChannelLeave | EnvelopeChannelMessageSend | EnvelopeChannelMessageUpdate | EnvelopeChannelMessageRemove | EnvelopeMatchCreateMessage | EnvelopeMatchDataSend | EnvelopeMatchJoin | EnvelopeMatchLeave | EnvelopeMatchmakerAdd | EnvelopeMatchmakerRemove | EnvelopePartyCreate | EnvelopePartyJoin | EnvelopePartyLeave | EnvelopePartyPromote | EnvelopePartyAccept | EnvelopePartyRemove | EnvelopePartyClose | EnvelopePartyJoinRequestList | EnvelopePartyMatchmakerAdd | EnvelopePartyMatchmakerRemove | EnvelopePartyDataSend | EnvelopeStatusFollow | EnvelopeStatusUnfollow | EnvelopeStatusUpdate | EnvelopePing | EnvelopePong


trait Channel extends js.Object {
  val id: js.UndefOr[String] = js.undefined
  val presences: js.UndefOr[js.Array[Presence]] = js.undefined
  val self: js.UndefOr[Presence] = js.undefined
  val roomName: js.UndefOr[String] = js.undefined
  val groupId: js.UndefOr[String] = js.undefined
  val userIdOne: js.UndefOr[String] = js.undefined // For DM messages only
  val userIdTwo: js.UndefOr[String] = js.undefined // For DM messages only
}

trait EnvelopeChannel extends js.Object {
  val channel: Channel
}

trait ChannelJoin extends js.Object {
  val target: js.UndefOr[String] = js.undefined
  val `type`: js.UndefOr[Number] = js.undefined
  val persistence: js.UndefOr[Boolean] = js.undefined
  val hidden: js.UndefOr[Boolean] = js.undefined
}

trait EnvelopeChannelJoin extends js.Object {
  val channelJoin: ChannelJoin
}

trait ChannelLeave extends js.Object {
  val channelId: String
}

trait EnvelopeChannelLeave extends js.Object {
  val channelLeave: ChannelLeave
}

trait ChannelMessageSend extends js.Object {
  val channelId: String
  val content: String
}

trait EnvelopeChannelMessageSend extends js.Object {
  val channelMessageSend: ChannelMessageSend
}

trait ChannelMessageUpdate extends js.Object {
  val channelId: String
  val messageId: String
  val content: String
}

trait EnvelopeChannelMessageUpdate extends js.Object {
  val channelMessageUpdate: ChannelMessageUpdate
}

trait ChannelMessageRemove extends js.Object {
  val channelId: String
  val messageId: String
}

trait EnvelopeChannelMessageRemove extends js.Object {
  val channelMessageRemove: ChannelMessageRemove
}

trait EnvelopeMatchCreateMessage extends js.Object {
  val matchCreate: js.Object
}

trait MatchDataMessageSend extends js.Object {
  val matchId: String
  val opCode: String
  val data: js.UndefOr[String] = js.undefined
  val presences: js.UndefOr[js.Array[Presence]] = js.undefined
  val reliable: js.UndefOr[Boolean] = js.undefined
}

trait EnvelopeMatchDataSend extends js.Object {
  val matchDataSend: MatchDataMessageSend
}

trait MatchJoinMessage extends js.Object {
  val id: String
  val metadata: js.Dictionary[String]
}

trait EnvelopeMatchJoin extends js.Object {
  val matchJoin: MatchJoinMessage
}

trait MatchLeaveMessage extends js.Object {
  val matchId: String
}

trait EnvelopeMatchLeave extends js.Object {
  val matchLeave: MatchLeaveMessage
}

trait MatchmakerAddMessage extends js.Object {
  val minCount: Number
  val maxCount: Number
  val query: String
  val stringProperties: js.Dictionary[String]
  val numericProperties: js.Dictionary[Number]
  val countMultiple: Number
}

trait EnvelopeMatchmakerAdd extends js.Object {
  val matchmakerAdd: MatchmakerAddMessage
}

trait MatchmakerRemoveMessage extends js.Object {
  val ticket: String
}

trait EnvelopeMatchmakerRemove extends js.Object {
  val matchmakerRemove: MatchmakerRemoveMessage
}

trait PartyCreateMessage extends js.Object {
  val open: Boolean
  val maxSize: Number
}

trait EnvelopePartyCreate extends js.Object {
  val partyCreate: PartyCreateMessage
}

trait PartyJoinMessage extends js.Object {
  val partyId: String
}

trait EnvelopePartyJoin extends js.Object {
  val partyJoin: PartyJoinMessage
}

trait PartyLeaveMessage extends js.Object {
  val partyId: String
}

trait EnvelopePartyLeave extends js.Object {
  val partyLeave: PartyLeaveMessage
}

trait PartyPromoteMessage extends js.Object {
  val partyId: String
  val presence: Presence
}

trait EnvelopePartyPromote extends js.Object {
  val partyPromote: PartyPromoteMessage
}

trait PartyAcceptMessage extends js.Object {
  val partyId: String
  val presence: Presence
}

trait EnvelopePartyAccept extends js.Object {
  val partyAccept: PartyAcceptMessage
}

trait PartyRemoveMessage extends js.Object {
  val partyId: String
  val presence: Presence
}

trait EnvelopePartyRemove extends js.Object {
  val partyRemove: PartyRemoveMessage
}

trait PartyCloseMessage extends js.Object {
  val partyId: String
}

trait EnvelopePartyClose extends js.Object {
  val partyClose: PartyCloseMessage
}

trait PartyJoinRequestListMessage extends js.Object {
  val partyId: String
}

trait EnvelopePartyJoinRequestList extends js.Object {
  val partyJoinRequestList: PartyJoinRequestListMessage
}

trait PartyMatchmakerAddMessage extends js.Object {
  val partyId: String
  val minCount: Number
  val maxCount: Number
  val query: String
  val stringProperties: js.Dictionary[String]
  val numericProperties: js.Dictionary[Double]
  val countMultiple: Number
}

trait EnvelopePartyMatchmakerAdd extends js.Object {
  val partyMatchmakerAdd: PartyMatchmakerAddMessage
}

trait PartyMatchmakerRemoveMessage extends js.Object {
  val partyId: String
  val ticket: String
}

trait EnvelopePartyMatchmakerRemove extends js.Object {
  val partyMatchmakerRemove: PartyMatchmakerRemoveMessage
}

trait PartyDataSendMessage extends js.Object {
  val partyId: String
  val opCode: Number
  val data: js.UndefOr[String] = js.undefined
}

trait EnvelopePartyDataSend extends js.Object {
  val partyDataSend: PartyDataSendMessage
}

trait StatusFollowMessage extends js.Object {
  val userIds: js.Array[String]
  val usernames: js.Array[String]
}

trait EnvelopeStatusFollow extends js.Object {
  val statusFollow: StatusFollowMessage
}

trait StatusUnfollowMessage extends js.Object {
  val userIds: js.Array[String]
}

trait EnvelopeStatusUnfollow extends js.Object {
  val statusUnfollow: StatusUnfollowMessage
}

trait StatusUpdateMessage extends js.Object {
  val status: js.UndefOr[String] = js.undefined
}

trait EnvelopeStatusUpdate extends js.Object {
  val statusUpdate: StatusUpdateMessage
}

trait EnvelopePing extends js.Object {
  val ping: js.Object
}

trait EnvelopePong extends js.Object {
  val pong: js.Object
}

trait SqlExecResult extends js.Object {
  val rowsAffected: Number
}

type SqlQueryResult = js.Array[js.Dictionary[js.Any]]

trait WalletLedgerList extends js.Object {
  val items: js.Array[WalletLedgerResult]
  val cursor: js.UndefOr[String] = js.undefined
}

trait ValidatePurchaseAppleRequest extends js.Object {
  val receipt: String
}

trait ValidateSubscriptionAppleRequest extends js.Object {
  val receipt: String
}

trait ValidatePurchaseGoogleRequest extends js.Object {
  val purchase: String
}

trait ValidateSubscriptionGoogleRequest extends js.Object {
  val receipt: String
}

trait ValidatePurchaseHuaweiRequest extends js.Object {
  val purchase: String
  val signature: String
}

trait ValidatePurchaseResponse extends js.Object {
  val validatedPurchases: js.UndefOr[js.Array[ValidatedPurchase]] = js.undefined
}

trait ValidateSubscriptionResponse extends js.Object {
  val validatedSubscription: ValidatedSubscription
}

trait ValidatedPurchaseOwner extends js.Object {
  val validatedPurchase: ValidatedPurchase
  val userId: String
}

trait ValidatedSubscriptionOwner extends js.Object {
  val validatedSubscription: ValidatedSubscription
  val userId: String
}

type ValidatedPurchaseStore = "APPLE_APP_STORE" | "GOOGLE_PLAY_STORE" | "HUAWEI_APP_GALLERY"

type ValidatedPurchaseEnvironment = "UNKNOWN" | "SANDBOX" | "PRODUCTION"

trait ValidatedPurchase extends js.Object {
  val userId: String
  val productId: String
  val transactionId: String
  val store: ValidatedPurchaseStore
  val purchaseTime: Number
  val createTime: Number
  val updateTime: Number
  val providerResponse: String
  val environment: ValidatedPurchaseEnvironment
  val seenBefore: Boolean
}

trait ValidatedSubscription extends js.Object {
  val userId: String
  val productId: String
  val originalTransactionId: String
  val store: ValidatedPurchaseStore
  val purchaseTime: Number
  val createTime: Number
  val updateTime: Number
  val environment: ValidatedPurchaseEnvironment
  val expiryTime: String
  val active: Boolean
}

trait ValidatedPurchaseList extends js.Object {
  val validatedPurchases: js.UndefOr[js.Array[ValidatedPurchase]] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
  val prevCursor: js.UndefOr[String] = js.undefined
}

trait ValidatedSubscriptionList extends js.Object {
  val validatedSubscription: js.UndefOr[ValidatedSubscription] = js.undefined
  val cursor: js.UndefOr[String] = js.undefined
  val prevCursor: js.UndefOr[String] = js.undefined
}

trait ChannelMessageSendAck extends js.Object {
  val channelId: String
  val messageId: String
  val code: Number
  val username: String
  val createTime: Number
  val updateTime: Number
  val persistent: Boolean
}


object PresenceReason {
  type Type = 0 | 1 | 2 | 3 | 4
  val PresenceReasonUnknown: Type = 0
  val PresenceReasonJoin: Type = 1
  val PresenceReasonUpdate: Type = 2
  val PresenceReasonLeave: Type = 3
  val PresenceReasonDisconnect: Type = 4
}

object ChanType {
  type Type = 1 | 2 | 3
  val Room: Type = 1
  val DirectMessage: Type = 2
  val Group: Type = 3
}

/**
 * The server APIs available in the game server.
 */
trait Nakama extends js.Object {
  /**
   * Convert binary data to string.
   *
   * @param data - Data to convert to string.
   * @throws TypeError
   */
  def binaryToString(data: js.typedarray.ArrayBuffer): String

  /**
   * Convert a string to binary data.
   *
   * @param str - String to convert to binary data.
   * @throws TypeError
   */
  def stringToBinary(str: String): js.typedarray.ArrayBuffer

  /**
   * Emit an event to be processed.
   *
   * @param eventName  - A string with the event name.
   * @param properties - A map of properties to send in the event.
   * @param timestamp  - (optional) Timestamp of the event as a Unix epoch.
   * @param external   - (optional) External (sclient side) generated event.
   * @throws TypeError
   */
  def event(eventName: String, properties: js.Dictionary[String], timestamp: js.UndefOr[Number] = js.undefined, external: js.UndefOr[Boolean] = js.undefined): Unit

  /**
   * Add a custom metrics counter.
   *
   * @param name  - The name of the custom metrics counter.
   * @param tags  - The metrics tags associated with this counter.
   * @param delta - An integer value to update this metric with.
   * @throws TypeError
   */
  def metricsCounterAdd(name: String, tags: js.Dictionary[String], delta: Number): Unit

  /**
   * Add a custom metrics gauge.
   *
   * @param name  - The name of the custom metrics gauge.
   * @param tags  - The metrics tags associated with this gauge.
   * @param value - A value to update this metric with.
   * @throws TypeError
   */
  def metricsGaugeSet(name: String, tags: js.Dictionary[String], value: Number): Unit


  /**
   * Add a custom metrics timer.
   *
   * @param name  - The name of the custom metrics timer.
   * @param tags  - The metrics tags associated with this timer.
   * @param value - An integer value to update this metric with (in nanoseconds).
   * @throws TypeError
   */
  def metricsTimerRecord(name: String, tags: js.Dictionary[String], value: Number): Unit

  /**
   * Generate a new UUID v4.
   *
   * @return UUID v4
   *
   */
  def uuidv4(): String

  /**
   * Execute an SQL query to the Nakama database.
   *
   * @param sqlQuery  - SQL Query string.
   * @param arguments - Opt. List of arguments to map to the query placeholders.
   * @return the number of affected rows.
   * @throws TypeError
   * @throws GoError
   */
  def sqlExec(sqlQuery: String, args: js.UndefOr[js.Array[js.Any]] = js.undefined): SqlExecResult

  /**
   * Get the results of an SQL query to the Nakama database.
   *
   * @param sqlQuery  - SQL Query string.
   * @param arguments - List of arguments to map to the query placeholders.
   * @return an array of the returned query rows, each one containing an object whose keys map a column to the row value.
   * @throws TypeError
   * @throws GoError
   */
  def sqlQuery(sqlQuery: String, args: js.UndefOr[js.Array[js.Any]] = js.undefined): SqlQueryResult

  /**
   * Http Request
   *
   * @param url     - Request target URL.
   * @param method  - Http method.
   * @param headers - Http request headers.
   * @param body    - Http request body.
   * @param timeout - Http Request timeout in ms.
   * @return Http response
   * @throws TypeError
   * @throws GoError
   */
  def httpRequest(url: String, method: RequestMethod, headers: js.UndefOr[js.Dictionary[String]] = js.undefined, body: js.UndefOr[String] = js.undefined, timeout: js.UndefOr[Number] = js.undefined): HttpResponse

  /**
   * Base 64 Encode
   *
   * @param string - Input to encode.
   * @return Base 64 encoded string.
   * @throws TypeError
   */
  def base64Encode(s: String | js.typedarray.ArrayBuffer, padding: js.UndefOr[Boolean] = js.undefined): String

  /**
   * Base 64 Decode
   *
   * @param string - Input to decode.
   * @return Decoded data as an ArrayBuffer.
   * @throws TypeError
   * @throws GoError
   */
  def base64Decode(s: String, padding: js.UndefOr[Boolean] = js.undefined): js.typedarray.ArrayBuffer

  /**
   * Base 64 URL Encode
   *
   * @param string - Input to encode.
   * @return URL safe base 64 encoded string.
   * @throws TypeError
   */
  def base64UrlEncode(s: String | js.typedarray.ArrayBuffer, padding: js.UndefOr[Boolean] = js.undefined): String

  /**
   * Base 64 URL Decode
   *
   * @param string - Input to decode.
   * @return Decoded data as an ArrayBuffer.
   * @throws TypeError
   * @throws GoError
   */
  def base64UrlDecode(s: String, padding: js.UndefOr[Boolean] = js.undefined): js.typedarray.ArrayBuffer

  /**
   * Base 16 Encode
   *
   * @param string - Input to encode.
   * @return Base 16 encoded string.
   * @throws TypeError
   */
  def base16Encode(s: String | js.typedarray.ArrayBuffer, padding: js.UndefOr[Boolean] = js.undefined): String

  /**
   * Base 16 Decode
   *
   * @param string - Input to decode.
   * @return Decoded data as an ArrayBuffer.
   * @throws TypeError
   * @throws GoError
   */
  def base16Decode(s: String, padding: js.UndefOr[Boolean] = js.undefined): js.typedarray.ArrayBuffer

  /**
   * Generate a JWT token
   *
   * @param algorithm  - JWT signing algorithm.
   * @param signingKey - Signing key.
   * @param claims     - JWT claims.
   * @return signed JWT token.
   * @throws TypeError
   * @throws GoError
   */
  def jwtGenerate(algorithm: "HS256" | "RS256", signingKey: String, claims: js.Dictionary[String | Number | Boolean]): String

  /**
   * AES 128 bit block size encrypt
   *
   * @param input - String to encrypt.
   * @param key   - Encryption key.
   * @return cipher text base64 encoded.
   * @throws TypeError
   * @throws GoError
   */
  def aes128Encrypt(input: String, key: String): String

  /**
   * AES 128 bit block size decrypt
   *
   * @param input - String to decrypt.
   * @param key   - Encryption key.
   * @return clear text.
   * @throws TypeError
   * @throws GoError
   */
  def aes128Decrypt(input: String, key: String): String

  /**
   * AES 256 bit block size encrypt
   *
   * @param input - String to encrypt.
   * @param key   - Encryption key.
   * @return cipher text base64 encoded.
   * @throws TypeError
   * @throws GoError
   */
  def aes256Encrypt(input: String, key: String): String

  /**
   * AES 256 bit block size decrypt
   *
   * @param input - String to decrypt.
   * @param key   - Encryption key.
   * @return clear text.
   * @throws TypeError
   * @throws GoError
   */
  def aes256Decrypt(input: String, key: String): String

  /**
   * MD5 Hash of the input
   *
   * @param input - String to hash.
   * @return md5 Hash.
   * @throws TypeError
   */
  def md5Hash(input: String): String

  /**
   * SHA256 Hash of the input
   *
   * @param input - String to hash.
   * @return sha256 Hash.
   * @throws TypeError
   */
  def sha256Hash(input: String): String

  /**
   * RSA SHA256 Hash of the input
   *
   * @param input - String to hash.
   * @param key   - RSA private key.
   * @return sha256 Hash.
   * @throws TypeError
   * @throws GoError
   */
  def rsaSha256Hash(input: String, key: String): String

  /**
   * def   * HMAC SHA256 of the input
   *
   * @param input - String to hash.
   * @param key   - secret key.
   * @return HMAC SHA256.
   * @throws TypeError
   * @throws GoError
   */
  def hmacSha256Hash(input: String, key: String): js.typedarray.ArrayBuffer

  /**
   * BCrypt hash of a password
   *
   * @param password - password to hash.
   * @return password bcrypt hash.
   * @throws TypeError
   * @throws GoError
   */
  def bcryptHash(password: String): String

  /**
   * Compare BCrypt password hash with password for a match.
   *
   * @param password - plaintext password.
   * @param hash     - hashed password.
   * @return true if hashed password and plaintext password match, false otherwise.
   * @throws TypeError
   * @throws GoError
   */
  def bcryptCompare(hash: String, password: String): Boolean

  /**
   * Authenticate with Apple.
   *
   * @param token    - Apple token.
   * @param username - username. If not provided a random username will be generated.
   * @param create   - create user if not exists, defaults to true
   * @return Object with authenticated user data.
   * @throws TypeError
   * @throws GoError
   */
  def authenticateApple(token: String, username: js.UndefOr[String] = js.undefined, create: js.UndefOr[Boolean] = js.undefined): AuthResult

  /**
   * Authenticate using a custom identifier.
   *
   * @param id       - custom identifier.
   * @param username - username. If not provided a random username will be generated.
   * @param create   - create user if not exists, defaults to true
   * @return Object with authenticated user data.
   * @throws TypeError
   * @throws GoError
   */
  def authenticateCustom(id: String, username: js.UndefOr[String] = js.undefined, create: js.UndefOr[Boolean] = js.undefined): AuthResult

  /**
   * Authenticate using a device identifier.
   *
   * @param id       - device identifier.
   * @param username - username. If not provided a random username will be generated.
   * @param create   - create user if not exists, defaults to true
   * @return Object with authenticated user data.
   * @throws TypeError
   * @throws GoError
   */
  def authenticateDevice(id: String, username: js.UndefOr[String] = js.undefined, create: js.UndefOr[Boolean] = js.undefined): AuthResult

  /**
   * Authenticate using email.
   *
   * @param email    - account email.
   * @param password - account password.
   * @param username - username. If not provided a random username will be generated.
   * @param create   - create user if not exists, defaults to true
   * @return Object with authenticated user data.
   * @throws TypeError
   * @throws GoError
   */
  def authenticateEmail(email: String, password: String, username: js.UndefOr[String] = js.undefined, create: js.UndefOr[Boolean] = js.undefined): AuthResult

  /**
   * Authenticate using Facebook account.
   *
   * @param token         - Facebook token.
   * @param importFriends - import FB account friends.
   * @param username      - username. If not provided a random username will be generated.
   * @param create        - create user if not exists, defaults to true
   * @return Object with authenticated user data.
   * @throws TypeError
   * @throws GoError
   */
  def authenticateFacebook(token: String, importFriends: js.UndefOr[Boolean] = js.undefined, username: js.UndefOr[String] = js.undefined, create: js.UndefOr[Boolean] = js.undefined): AuthResult

  /**
   * Authenticate using Facebook Instant Game.
   *
   * @param signedPlayerInfo - Facebook Instant Game signed player info.
   * @param username         - username. If not provided a random username will be generated.
   * @param create           - create user if not exists, defaults to true
   * @return Object with authenticated user data.
   * @throws TypeError
   * @throws GoError
   */
  def authenticateFacebookInstantGame(signedPlayerInfo: String, username: js.UndefOr[String] = js.undefined, create: js.UndefOr[Boolean] = js.undefined): AuthResult

  /**
   * Authenticate using Apple Game center.
   *
   * @param playerId     - Game center player ID.
   * @param bundleId     - Game center bundle ID.
   * @param ts           - Timestamp.
   * @param salt         - Salt.
   * @param signature    - Signature.
   * @param publicKeyURL - Public Key URL.
   * @param username     - username. If not provided a random username will be generated.
   * @param create       - create user if not exists, defaults to true
   * @return Object with authenticated user data.
   * @throws TypeError
   * @throws GoError
   */
  def authenticateGameCenter(playerId: String, bundleId: String, ts: Number, salt: String, signature: String, publicKeyURL: String, username: js.UndefOr[String] = js.undefined, create: js.UndefOr[Boolean] = js.undefined): AuthResult

  /**
   * Authenticate with Google account.
   *
   * @param token    - Google token.
   * @param username - username. If not provided a random username will be generated.
   * @param create   - create user if not exists, defaults to true
   * @return Object with authenticated user data.
   * @throws TypeError
   * @throws GoError
   */
  def authenticateGoogle(token: String, username: js.UndefOr[String] = js.undefined, create: js.UndefOr[Boolean] = js.undefined): AuthResult

  /**
   * Authenticate with Steam account.
   *
   * @param token    - Steam token.
   * @param username - username. If not provided a random username will be generated.
   * @param create   - create user if not exists, defaults to true
   * @return Object with authenticated user data.
   * @throws TypeError
   * @throws GoError
   */
  def authenticateSteam(token: String, username: js.UndefOr[String] = js.undefined, create: js.UndefOr[Boolean] = js.undefined): AuthResult

  /**
   * Generate authentication token.
   *
   * @param userId   - User ID.
   * @param username - Opt. Username. If not provided one will be automatically generated.
   * @param exp      - Opt. Token expiration, Unix epoch.
   * @param vars     - Opt. Arbitrary metadata.
   * @return Object with authenticated user data.
   * @throws TypeError
   * @throws GoError
   */
  def authenticateTokenGenerate(userId: String, username: js.UndefOr[String] = js.undefined, exp: js.UndefOr[Number] = js.undefined, vars: js.UndefOr[js.Dictionary[String]] = js.undefined): TokenGenerateResult

  /**
   * Get account data by id.
   *
   * @param userId - User ID.
   * @return Object with account data.
   * @throws TypeError
   * @throws GoError
   */
  def accountGetId(userId: String): Account

  /**
   * Get accounts data by ids.
   *
   * @param userIds - User IDs.
   * @return Array containing accounts data.
   * @throws TypeError
   * @throws GoError
   */
  def accountsGetId(userIds: js.Array[String]): js.Array[Account]

  /**
   * Update user account.
   *
   * @param userId      - User ID for which the information is to be updated.
   * @param username    - Username to be updated. Use null or undefined to not update this field.
   * @param displayName - Display name to be updated. Use null or undefined to not update this field.
   * @param timezone    - Timezone to be updated. Use null or undefined to not update this field.
   * @param location    - Location to be updated. Use null or undefined to not update this field.
   * @param language    - Language to be updated. Use null or undefined to not update this field.
   * @param avatar      - User's avatar URL. Use null or undefined to not update this field.
   * @param metadata    - Metadata to update. Use null or undefined not to update this field.
   * @throws TypeError
   * @throws GoError
   */
  def accountUpdateId(userId: String, username: js.UndefOr[String | Null] = js.undefined, displayName: js.UndefOr[String | Null] = js.undefined, timezone: js.UndefOr[String | Null] = js.undefined, location: js.UndefOr[String | Null] = js.undefined, language: js.UndefOr[String | Null] = js.undefined, avatar: js.UndefOr[String | Null] = js.undefined, metadata: js.UndefOr[js.Dictionary[js.Any] | Null] = js.undefined): Unit

  /**
   * Delete user account
   *
   * @param userId   - Target account.
   * @param recorded - Opt. Whether to record this deletion in the database. Defaults to false.
   * @throws TypeError
   * @throws GoError
   */
  def accountDeleteId(userId: String, recorded: js.UndefOr[Boolean] = js.undefined): Unit

  /**
   * Export user account data to JSON encoded string
   *
   * @param userId - Target account.
   * @throws TypeError
   * @throws GoError
   */
  def accountExportId(userId: String): String

  /**
   * Get user data by ids.
   *
   * @param userIds     - User IDs. Pass null to fetch by facebookIds only.
   * @param facebookIds - Facebook IDs.
   * @throws TypeError
   * @throws GoError
   */
  def usersGetId(userIds: js.Array[String], facebookIds: js.UndefOr[js.Array[String]] = js.undefined): js.Array[User]

  /**
   * Get user data by usernames.
   *
   * @param usernames - Usernames.
   * @throws TypeError
   * @throws GoError
   */
  def usersGetUsername(usernames: js.Array[String]): js.Array[User]

  /**
   * Get user data for a given number of random users.
   *
   * @param count - Number of users to retrieve.
   * @throws TypeError
   * @throws GoError
   */
  def usersGetRandom(count: Number): js.Array[User]

  /**
   * Ban a group of users by id.
   *
   * @param userIds - User IDs.
   * @throws TypeError
   * @throws GoError
   */
  def usersBanId(userIds: js.Array[String]): Unit

  /**
   * Unban a group of users by id.
   *
   * @param userIds - User IDs.
   * @throws TypeError
   * @throws GoError
   */
  def usersUnbanId(userIds: js.Array[String]): Unit

  /**
   * Link an account to Apple sign in.
   *
   * @param userId - User ID.
   * @param token  - Apple sign in token.
   * @throws TypeError
   * @throws GoError
   */
  def linkApple(userId: String, token: String): Unit

  /**
   * Link an account to a customID.
   *
   * @param userId   - User ID.
   * @param customID - Custom ID.
   * @throws TypeError
   * @throws GoError
   */
  def linkCustom(userId: String, customID: String): Unit

  /**
   * Link account to a custom device.
   *
   * @param userId   - User ID.
   * @param deviceID - Device ID.
   * @throws TypeError
   * @throws GoError
   */
  def linkDevice(userId: String, deviceID: String): Unit

  /**
   * Link account to username and password.
   *
   * @param userId   - User ID.
   * @param email    - Email.
   * @param password - Password.
   * @throws TypeError
   * @throws GoError
   */
  def linkEmail(userId: String, email: String, password: String): Unit

  /**
   * Link account to Facebook.
   *
   * @param userId        - User ID.
   * @param username      - Username.
   * @param token         - Facebook Token.
   * @param importFriends - Import Facebook Friends. Defaults to true.
   * @throws TypeError
   * @throws GoError
   */
  def linkFacebook(userId: String, username: String, token: String, importFriends: js.UndefOr[Boolean] = js.undefined): Unit

  /**
   * Link account to Facebook Instant Games.
   *
   * @param userId           - User ID.
   * @param signedPlayerInfo - Signed player info.
   * @throws TypeError
   * @throws GoError
   */
  def linkFacebookInstantGame(userId: String, signedPlayerInfo: String): Unit

  /**
   * Link account to Apple Game Center.
   *
   * @param userId       - User ID.
   * @param playerId     - Game center player ID.
   * @param bundleId     - Game center bundle ID.
   * @param ts           - Timestamp.
   * @param salt         - Salt.
   * @param signature    - Signature.
   * @param publicKeyURL - Public Key URL.
   * @throws TypeError
   * @throws GoError
   */
  def linkGameCenter(userId: String, playerId: String, bundleId: String, ts: Number, salt: String, signature: String, publicKeyURL: String): Unit

  /**
   * Link account to Google.
   *
   * @param userId - User ID.
   * @param token  - Google Token.
   * @throws TypeError
   * @throws GoError
   */
  def linkGoogle(userId: String, token: String): Unit

  /**
   * Link account to Steam.
   *
   * @param userId        - User ID.
   * @param username      - Username.
   * @param token         - Steam Token.
   * @param importFriends - Import Steam Friends. Defaults to true.
   * @throws TypeError
   * @throws GoError
   */
  def linkSteam(userId: String, username: String, token: String, importFriends: Boolean): Unit

  /**
   * Unlink Apple sign in from an account.
   *
   * @param userId - User ID.
   * @param token  - Apple sign in token.
   * @throws TypeError
   * @throws GoError
   */
  def unlinkApple(userId: String, token: String): Unit

  /**
   * Unlink a customID from an account.
   *
   * @param userId   - User ID.
   * @param customID - Custom ID.
   * @throws TypeError
   * @throws GoError
   */
  def unlinkCustom(userId: String, customID: String): Unit

  /**
   * Unlink a custom device from an account.
   *
   * @param userId   - User ID.
   * @param deviceID - Device ID.
   * @throws TypeError
   * @throws GoError
   */
  def unlinkDevice(userId: String, deviceID: String): Unit

  /**
   * Unlink username and password from an account.
   *
   * @param userId - User ID.
   * @param email  - Email.
   * @throws TypeError
   * @throws GoError
   */
  def unlinkEmail(userId: String, email: String): Unit

  /**
   * Unlink Facebook from an account.
   *
   * @param userId - User ID.
   * @param token  - Password.
   * @throws TypeError
   * @throws GoError
   */
  def unlinkFacebook(userId: String, token: String): Unit

  /**
   * Unlink Facebook Instant Games from an account.
   *
   * @param userId           - User ID.
   * @param signedPlayerInfo - Signed player info.
   * @throws TypeError
   * @throws GoError
   */
  def unlinkFacebookInstantGame(userId: String, signedPlayerInfo: String): Unit

  /**
   * Unlink Apple Game Center from an account.
   *
   * @param userId       - User ID.
   * @param playerId     - Game center player ID.
   * @param bundleId     - Game center bundle ID.
   * @param ts           - Timestamp.
   * @param salt         - Salt.
   * @param signature    - Signature.
   * @param publicKeyURL - Public Key URL.
   * @throws TypeError
   * @throws GoError
   */
  def unlinkGameCenter(userId: String, playerId: String, bundleId: String, ts: Number, salt: String, signature: String, publicKeyURL: String): Unit

  /**
   * Unlink Google from account.
   *
   * @param userId - User ID.
   * @param token  - Google token.
   * @throws TypeError
   * @throws GoError
   */
  def unlinkGoogle(userId: String, token: String): Unit

  /**
   * Unlink Steam from an account.
   *
   * @param userId - User ID.
   * @param token  - Steam token.
   * @throws TypeError
   * @throws GoError
   */
  def unlinkSteam(userId: String, token: String): Unit

  /**
   * List stream presences.
   *
   * @param stream           - Stream object.
   * @param includeHidden    - Opt. Include hidden presences in the list or not, default true.
   * @param includeNotHidden - Opt. Include not hidden presences in the list or not, default true.
   * @return List of presence objects.
   * @throws TypeError
   */
  def streamUserList(stream: Stream, includeHidden: js.UndefOr[Boolean] = js.undefined, includeNotHidden: js.UndefOr[Boolean] = js.undefined): js.Array[Presence]

  /**
   * Get presence of user in a stream.
   *
   * @param userId    - User ID.
   * @param sessionID - Session ID.
   * @param stream    - Stream data.
   * @throws TypeError
   * @return Presence object.
   */
  def streamUserGet(userId: String, sessionID: String, stream: Stream): Presence

  /**
   * Add a user to a stream.
   *
   * @param userId      - User ID.
   * @param sessionID   - Session ID.
   * @param stream      - Stream data.
   * @param hidden      - Opt. If hidden no presence events are generated for the user.
   * @param persistence - Opt. By default persistence is enabled, if the stream supports it.
   * @param status      - Opt. By default no status is set for the user.
   * @throws TypeError
   * @throws GoError
   */
  def streamUserJoin(userId: String, sessionID: String, stream: Stream, hidden: js.UndefOr[Boolean] = js.undefined, persistence: js.UndefOr[Boolean], status: js.UndefOr[String] = js.undefined): Unit

  /**
   * Update user status in a stream.
   *
   * @param userId      - User ID.
   * @param sessionID   - Session ID.
   * @param stream      - Stream data.
   * @param hidden      - Opt. If hidden no presence events are generated for the user.
   * @param persistence - Opt. By default persistence is enabled, if the stream supports it.
   * @param status      - Opt. By default no status is set for the user.
   * @throws TypeError
   * @throws GoError
   */
  def streamUserUpdate(userId: String, sessionID: String, stream: Stream, hidden: js.UndefOr[Boolean] = js.undefined, persistence: js.UndefOr[Boolean] = js.undefined, status: js.UndefOr[String] = js.undefined): Unit

  /**
   * Have a user leave a stream.
   *
   * @param userId    - User ID.
   * @param sessionID - Session ID.
   * @param stream    - Stream data.
   * @throws TypeError
   * @throws GoError
   */
  def streamUserLeave(userId: String, sessionID: String, stream: Stream): Unit

  /**
   * Kick user from a stream.
   *
   * @param presence - User presence data.
   * @param stream   - Stream data.
   * @throws TypeError
   * @throws GoError
   */
  def streamUserKick(presence: Presence, stream: Stream): Unit

  /**
   * Count the users in a stream.
   *
   * @param stream - Stream data.
   * @return the number of users in the stream.
   * @throws TypeError
   */
  def streamCount(stream: Stream): Number

  /**
   * Close a stream.
   *
   * Closing a stream removes all presences currently on it. It can be useful to explicitly close a stream and enable the server to reclaim resources more quickly.
   *
   * @param stream - Stream data.
   * @return the number of users in the stream.
   * @throws TypeError
   */
  def streamClose(stream: Stream): Unit

  /**
   * Send data to users in a stream.
   *
   * @param stream    - Stream data.
   * @param data      - Data string to send.
   * @param presences - Opt. List of presences in the stream to send the data to. If null or empty, data is sent to all the users.
   * @param reliable  - Opt. If data is sent with delivery guarantees. Defaults to true.
   * @throws TypeError
   */
  def streamSend(stream: Stream, data: String, presences: js.UndefOr[js.Array[Presence] | Null] = js.undefined, reliable: js.UndefOr[Boolean] = js.undefined): Unit

  /**
   * Send envelope data to users in a stream.
   *
   * @param stream    - Stream data.
   * @param envelope  - Envelope object.
   * @param presences - Opt. List of presences in the stream to send the data to. If null or empty, data is sent to all the users.
   * @param reliable  - Opt. If data is sent with delivery guarantees. Defaults to true.
   * @throws TypeError
   * @throws GoError
   */
  def streamSendRaw(stream: Stream, envelope: {}, presences: js.UndefOr[js.Array[Presence] | Null] = js.undefined, reliable: js.UndefOr[Boolean] = js.undefined): Unit

  /**
   * Disconnect session.
   *
   * @param sessionID - Session ID.
   * @param sessionID - Opt. Presence disconnect reason.
   * @throws TypeError
   * @throws GoError
   */
  def sessionDisconnect(sessionID: String, reason: js.UndefOr[PresenceReason.Type] = js.undefined): Unit

  /**
   * Log out a user from their current session.
   *
   * @param userId       - The ID of the user to be logged out.
   * @param token        - Opt. The current session authentication token.
   * @param refreshToken - Opt. The current session refresh token.
   * @throws TypeError
   * @throws GoError
   */
  def sessionLogout(userId: String, token: js.UndefOr[String] = js.undefined, refreshToken: js.UndefOr[String] = js.undefined): Unit

  /**
   * Create a new match.
   *
   * @param module - Name of the module the match will run.
   * @param params - Opt. Object with the initial state of the match.
   * @return the match ID of the created match.
   * @throws TypeError
   * @throws GoError
   */
  def matchCreate(module: String, params: js.UndefOr[js.Dictionary[js.Any]] = js.undefined): String

  /**
   * Get a running match info.
   *
   * @param id - Match ID.
   * @return match data.
   * @throws TypeError
   * @throws GoError
   */
  def matchGet(id: String): Match | Null

  /**
   * Find matches with filters.
   *
   * @param limit         - Opt. Max number of matches to return. Defaults to 1.
   * @param authoritative - Filter authoritative or non-authoritative matches. If NULL or no value is provided, both authoritative and non-authoritative match.
   * @param label         - Filter by a label. If null or no value is provided, all labels are matched.
   * @param minSize       - Filter by min number of players in a match. If NULL or no value is provided, there is no lower player bound.
   * @param maxSize       - Filter by max number of players in a match. If NULL or no value is provided, there is no upper player bound.
   * @param query         - Query by match properties (https://heroiclabs.com/docs/gameplay-matchmaker/#query). If no value is provided, all properties match.
   * @return list of running game matches that match the specified filters.
   * @throws TypeError
   * @throws GoError
   */
  def matchList(limit: Number, authoritative: js.UndefOr[Boolean | Null] = js.undefined, label: js.UndefOr[String | Null] = js.undefined, minSize: js.UndefOr[Number | Null] = js.undefined, maxSize: js.UndefOr[Number | Null] = js.undefined, query: js.UndefOr[String | Null] = js.undefined): js.Array[Match]

  /**
   * Signal a match and receive a response.
   *
   * @param id   - Match ID.
   * @param data - Arbitrary data to pass to the match signal handler.
   * @return response data from the signal handler, if any.
   * @throws TypeError
   * @throws GoError
   */
  def matchSignal(id: String, data: String): String


  /**
   * Send a notification.
   *
   * @param userId     - User ID.
   * @param subject    - Subject of the notification.
   * @param content    - Key value object to send as the notification content.
   * @param code       - Custom code for the notification. Must be a positive integer.
   * @param senderID   - Opt. Sender ID. Defaults to nil - sender sent.
   * @param persistent - Opt. A non-persistent message will only be received by a client which is currently connected to the server. Defaults to false.
   * @throws TypeError
   * @throws GoError
   */
  def notificationSend(userId: String, subject: String, content: js.Dictionary[js.Any], code: Number, senderID: js.UndefOr[String | Null] = js.undefined, persistent: js.UndefOr[Boolean] = js.undefined): Unit

  /**
   * Send multiple notifications.
   *
   * @param notifications - Array of notifications.
   * @throws TypeError
   * @throws GoError
   */
  def notificationsSend(notifications: js.Array[NotificationRequest]): Unit

  /**
   * Send an in-app notification to all users.
   *
   * @param subject    - Subject of the notification.
   * @param content    - Key value object to send as the notification content.
   * @param code       - Custom code for the notification. Must be a positive integer.
   * @param persistent - Opt. A non-persistent message will only be received by a client which is currently connected to the server. Defaults to false.
   * @throws TypeError
   * @throws GoError
   */
  def notificationSendAll(subject: String, content: js.Dictionary[js.Any], code: Number, persistent: js.UndefOr[Boolean] = js.undefined): Unit

  /**
   * Delete multiple notifications.
   *
   * @param notifications - Array of notifications to delete.
   * @throws TypeError
   * @throws GoError
   */
  def notificationsDelete(notifications: js.Array[NotificationDeleteRequest]): Unit

  /**
   * Update user wallet.
   *
   * @param userId       - User ID.
   * @param changeset    - Object with the wallet changeset data.
   * @param metadata     - Opt. Additional metadata to tag the wallet update with.
   * @param updateLedger - Opt. Whether to record this update in the ledger. Default true.
   * @throws TypeError
   * @throws GoError
   */
  def walletUpdate(userId: String, changeset: js.Dictionary[Number], metadata: js.UndefOr[js.Dictionary[js.Any]] = js.undefined, updateLedger: js.UndefOr[Boolean] = js.undefined): WalletUpdateResult

  /**
   * Update multiple user wallets.
   *
   * @param updates      - The set of user wallet update operations to apply.
   * @param updateLedger - Opt. Whether to record this update in the ledger. Default true.
   * @throws TypeError
   * @throws GoError
   */
  def walletsUpdate(updates: js.Array[WalletUpdate], updateLedger: js.UndefOr[Boolean]): js.Array[WalletUpdateResult]

  /**
   * Update user wallet ledger.
   *
   * @param ledgerID - The ledger id.
   * @param metadata - Additional metadata to tag the wallet update with.
   * @return updated ledger data.
   * @throws TypeError
   * @throws GoError
   */
  def walletLedgerUpdate(ledgerID: String, metadata: js.Dictionary[js.Any]): WalletLedgerResult

  /**
   * Update user wallet ledger.
   *
   * @param userId - User ID
   * @param limit  - Opt. Maximum number of items to list. Defaults to 100.
   * @param cursor - Opt. Pagination cursor.
   * @return Object containing an array of wallet ledger results and a cursor for the next page of results, if there is one.
   * @throws TypeError
   * @throws GoError
   */
  def walletLedgerList(userId: String, limit: js.UndefOr[Number] = js.undefined, cursor: js.UndefOr[String] = js.undefined): WalletLedgerList

  /**
   * List user's storage objects from a collection.
   *
   * @param userId     - Opt. User ID that owns the collection. Call with null to retrieve regardless of the owner.
   * @param collection - Opt. Storage collection.
   * @param limit      - Opt. Maximum number of items to list. Defaults to 100.
   * @param cursor     - Opt. Pagination cursor.
   * @return Object containing an array of storage objects and a cursor for the next page of results, if there is one.
   * @throws TypeError
   * @throws GoError
   */
  def storageList(userId: js.UndefOr[String | Null], collection: String, limit: js.UndefOr[Number] = js.undefined, cursor: js.UndefOr[String] = js.undefined): StorageObjectList

  /**
   * Get all storage objects matching the parameters.
   *
   * @param keys - Array of storage read objects.
   * @return Object containing an array of storage objects and a cursor for the next page of results, if there is one.
   * @throws TypeError
   * @throws GoError
   */
  def storageRead(keys: js.Array[StorageReadRequest]): js.Array[StorageObject]

  /**
   * Write storage objects.
   *
   * @param keys - Array of storage objects to write.
   * @return List of written objects acks.
   * @throws TypeError
   * @throws GoError
   */
  def storageWrite(keys: js.Array[StorageWriteRequest]): js.Array[StorageWriteAck]

  /**
   * Delete storage objects.
   *
   * @param keys - Array of storage objects to write.
   * @return List of written objects acks.
   * @throws TypeError
   * @throws GoError
   */
  def storageDelete(keys: js.Array[StorageDeleteRequest]): Unit

  /**
   * Update multiple entities.
   * Passing null to any of the arguments will ignore the corresponding update.
   *
   * @param accountUpdates        - Array of account updates.
   * @param storageObjectsUpdates - Array of storage objects updates.
   * @param walletUpdates         - Array of wallet updates.
   * @param updateLedger          - Opt. Wether if the wallet update should also update the wallet ledger. Defaults to false.
   * @return An object with the results from wallets and storage objects updates.
   * @throws TypeError
   * @throws GoError
   */
  def multiUpdate(accountUpdates: js.Array[UserUpdateAccount] | Null, storageObjectsUpdates: js.Array[StorageWriteRequest] | Null, walletUpdates: js.Array[WalletUpdate] | Null, updateLedger: js.UndefOr[Boolean] = js.undefined): MultiUpdateReturn

  trait MultiUpdateReturn extends js.Object {
    val storageWriteAcks: js.Array[StorageWriteAck]
    val walletUpdateAcks: js.Array[WalletUpdateResult]
  }

  /**
   * Create a new leaderboard.
   *
   * @param leaderboardID - Leaderboard id.
   * @param authoritative - Opt. Authoritative Leaderboard if true.
   * @param sortOrder     - Opt. Sort leaderboard in desc or asc order. Defauts to "desc".
   * @param operator      - Opt. Score operator "best", "set" or "incr" (refer to the docs for more info). Defaults to "best".
   * @param resetSchedule - Cron string to set the periodicity of the leaderboard reset. Set as null to never reset.
   * @param metadata      - Opt. metadata object.
   * @throws TypeError
   * @throws GoError
   */
  def leaderboardCreate(leaderboardID: String, authoritative: Boolean, sortOrder: js.UndefOr[SortOrder.Type] = js.undefined, operator: js.UndefOr[Operator.Type] = js.undefined, resetSchedule: js.UndefOr[String | Null] = js.undefined, metadata: js.UndefOr[js.Dictionary[js.Any]] = js.undefined): Unit

  /**
   * Delete a leaderboard.
   *
   * @param leaderboardID - Leaderboard id.
   * @throws TypeError
   * @throws GoError
   */
  def leaderboardDelete(leaderboardID: String): Unit

  /**
   * Get a list of tournaments by id.
   *
   * @param categoryStart - Filter leaderboard with categories greater or equal than this value.
   * @param categoryEnd   - Filter leaderboard with categories equal or less than this value.
   * @param limit         - Return only the required number of leaderboard denoted by this limit value.
   * @param cursor        - Cursor to paginate to the next result set. If this is empty/null there is no further results.
   * @return The leaderboard data for the given ids.
   * @throws TypeError
   * @throws GoError
   */
  def leaderboardList(categoryStart: js.UndefOr[Number] = js.undefined, categoryEnd: js.UndefOr[Number] = js.undefined, limit: js.UndefOr[Number] = js.undefined, cursor: js.UndefOr[String] = js.undefined): LeaderboardList

  /**
   * List records of a leaderboard.
   *
   * @param leaderboardID     - Leaderboard id.
   * @param leaderboardOwners - Array of leaderboard owners.
   * @param limit             - Max number of records to return.
   * @param cursor            - Page cursor.
   * @param overrideExpiry    - Override the time expiry of the leaderboard. (Unix epoch).
   * @return a list of leaderboard records.
   * @throws TypeError
   * @throws GoError
   */
  def leaderboardRecordsList(leaderboardID: String, leaderboardOwners: js.UndefOr[js.Array[String]] = js.undefined, limit: js.UndefOr[Number] = js.undefined, cursor: js.UndefOr[String] = js.undefined, overrideExpiry: js.UndefOr[Number] = js.undefined): LeaderboardRecordList

  /**
   * Write a new leaderboard record.
   *
   * @param leaderboardID - Leaderboard id.
   * @param ownerID       - Array of leaderboard owners.
   * @param username      - Username of the scorer.
   * @param score         - Score.
   * @param subscore      - Subscore.
   * @param metadata      - Opt. Metadata object.
   * @param operator      - Opt. Override the leaderboard operator.
   * @return - The created leaderboard record.
   * @throws TypeError
   * @throws GoError
   */
  def leaderboardRecordWrite(leaderboardID: String, ownerID: String, username: js.UndefOr[String] = js.undefined, score: js.UndefOr[Number] = js.undefined, subscore: js.UndefOr[Number], metadata: js.UndefOr[js.Dictionary[js.Any]] = js.undefined, operator: js.UndefOr[OverrideOperator.Type] = js.undefined): LeaderboardRecord

  /**
   * Delete a leaderboard record.
   *
   * @param leaderboardID - Leaderboard id.
   * @param ownerID       - Array of leaderboard owners.
   * @throws TypeError
   * @throws GoError
   */
  def leaderboardRecordDelete(leaderboardID: String, ownerID: String): Unit

  /**
   * Get a list of leaderboards by id.
   *
   * @param leaderboardIds - Leaderboard ids.
   * @return The leaderboard data for the given ids.
   * @throws TypeError
   * @throws GoError
   */
  def leaderboardsGetId(leaderboardIds: js.Array[String]): js.Array[Leaderboard]

  /**
   * Fetch the list of leaderboard records around the owner.
   *
   * @param leaderboardId  - The unique identifier for the leaderboard.
   * @param ownerId        - The owner of the score to list records around. Mandatory field.
   * @param limit          - Return only the required number of leaderboard records denoted by this limit value.
   * @param cursor         - Page cursor.
   * @param overrideExpiry - Records with expiry in the past are not returned unless within this defined limit. Must be equal or greater than 0.
   * @return The leaderboard records according to ID.
   * @throws TypeError
   * @throws GoError
   */
  def leaderboardRecordsHaystack(leaderboardId: String, ownerId: String, limit: Number, cursor: String, overrideExpiry: Number): LeaderboardRecordList

  /**
   * Create a new tournament.
   *
   * @param tournamentID  - Tournament id.
   * @param authoritative - Opt. Whether or not to only allow authoritative score submissions.
   * @param sortOrder     - Opt. Sort tournament in desc or asc order. Defaults to "desc".
   * @param operator      - Opt. Score operator "best", "set" or "incr" (refer to the docs for more info). Defaults to "best".
   * @param duration      - Opt. Duration of the tournament (unix epoch).
   * @param resetSchedule - Opt. Tournament reset schedule (cron syntax).
   * @param metadata      - Opt. metadata object.
   * @param title         -  Opt. Tournament title.
   * @param description   - Opt. Tournament description.
   * @param category      - Opt. Tournament category (1-127).
   * @param startTime     - Opt. Tournament start time (unix epoch).
   * @param endTime       - Opt. Tournament end time (unix epoch).
   * @param maxSize       - Opt. Maximum size of participants in a tournament.
   * @param maxNumScore   - Opt. Maximum submission attempts for a tournament record.
   * @param joinRequired  - Opt. Whether the tournament needs to be joint before a record write is allowed.
   * @throws TypeError
   * @throws GoError
   */
  def tournamentCreate(tournamentID: String,
                       authoritative: Boolean,
                       sortOrder: SortOrder.Type,
                       operator: Operator.Type,
                       duration: js.UndefOr[Number] = js.undefined,
                       resetSchedule: js.UndefOr[String] = js.undefined,
                       metadata: js.UndefOr[js.Dictionary[js.Any] | Null] = js.undefined,
                       title: js.UndefOr[String | Null] = js.undefined,
                       description: js.UndefOr[String | Null] = js.undefined,
                       category: js.UndefOr[Number | Null] = js.undefined,
                       startTime: js.UndefOr[Number | Null] = js.undefined,
                       endTime: js.UndefOr[Number | Null] = js.undefined,
                       maxSize: js.UndefOr[Number | Null] = js.undefined,
                       maxNumScore: js.UndefOr[Number | Null] = js.undefined,
                       joinRequired: js.UndefOr[Boolean] = js.undefined): Unit

  /**
   * Delete a tournament.
   *
   * @param tournamentID - Tournament id.
   * @throws TypeError
   * @throws GoError
   */
  def tournamentDelete(tournamentID: String): Unit

  /**
   * Add additional score attempts to the owner's tournament record.
   *
   * @param tournamentID - Tournament id.
   * @param ownerID      - Owner of the record id.
   * @param count        - Attempt count to add.
   * @throws TypeError
   * @throws GoError
   */
  def tournamentAddAttempt(tournamentID: String, ownerID: String, count: Number): Unit

  /**
   * Join a tournament.
   *
   * A tournament may need to be joined before the owner can submit scores.
   *
   * @param tournamentID - Tournament id.
   * @param userId       - Owner of the record id.
   * @param username     - The username of the record owner.
   * @throws TypeError
   * @throws GoError
   */
  def tournamentJoin(tournamentID: String, userId: String, username: String): Unit

  /**
   * Get a list of tournaments by id.
   *
   * @param tournamentIDs - Tournament ids.
   * @return The tournament data for the given ids.
   * @throws TypeError
   * @throws GoError
   */
  def tournamentsGetId(tournamentIds: js.Array[String]): js.Array[Tournament]

  /**
   * Get a list of tournaments by id.
   *
   * @param categoryStart - Filter tournament with categories greater or equal than this value.
   * @param categoryEnd   - Filter tournament with categories equal or less than this value.
   * @param startTime     - Filter tournament with that start after this time.
   * @param endTime       - Filter tournament with that end before this time.
   * @param limit         - Return only the required number of tournament denoted by this limit value.
   * @param cursor        - Cursor to paginate to the next result set. If this is empty/null there is no further results.
   * @return The tournament data for the given ids.
   * @throws TypeError
   * @throws GoError
   */
  def tournamentList(categoryStart: js.UndefOr[Number] = js.undefined, categoryEnd: js.UndefOr[Number] = js.undefined, startTime: js.UndefOr[Number] = js.undefined, endTime: js.UndefOr[Number] = js.undefined, limit: js.UndefOr[Number] = js.undefined, cursor: js.UndefOr[String] = js.undefined): TournamentList

  /**
   * List records of a tournament.
   *
   * @param tournamentID     - Tournament id.
   * @param tournamentOwners - Array of tournament owners.
   * @param limit            - Max number of records to return.
   * @param cursor           - Page cursor.
   * @param overrideExpiry   - Override the time expiry of the leaderboard. (Unix epoch).
   * @return a list of tournament records.
   * @throws TypeError
   * @throws GoError
   */
  def tournamentRecordsList(tournamentID: String, tournamentOwners: js.UndefOr[js.Array[String]] = js.undefined, limit: js.UndefOr[Number] = js.undefined, cursor: js.UndefOr[String] = js.undefined, overrideExpiry: js.UndefOr[Number] = js.undefined): TournamentRecordList

  /**
   * Submit a score and optional subscore to a tournament leaderboard.
   *
   * @param id       - The unique identifier for the leaderboard to submit to. Mandatory field.
   * @param ownerID  - The owner of this score submission. Mandatory field.
   * @param username - Opt. The owner username of this score submission, if it's a user.
   * @param score    - Opt. The score to submit. Optional in Lua. Default 0.
   * @param subscore - Opt. A secondary subscore parameter for the submission. Optional in Lua. Default 0.
   * @param metadata - Opt. The metadata you want associated to this submission.
   * @param operator - Opt. Override the tournament operator.
   * @return The tournament data for the given ids.
   * @throws TypeError
   * @throws GoError
   */
  def tournamentRecordWrite(id: String, ownerID: String, username: js.UndefOr[String] = js.undefined, score: js.UndefOr[Number] = js.undefined, subscore: js.UndefOr[Number] = js.undefined, metadata: js.UndefOr[js.Dictionary[js.Any]] = js.undefined, operator: js.UndefOr[OverrideOperator.Type] = js.undefined): LeaderboardRecord

  /**
   * Fetch the list of tournament records around the owner.
   *
   * @param id      - The unique identifier for the leaderboard to submit to. Mandatory field.
   * @param ownerId - The owner of this score submission. Mandatory field.
   * @param limit   - Opt. The owner username of this score submission, if it's a user.
   * @param cursor  - Page cursor.
   * @param expiry  - Opt. Expiry Unix epoch.
   * @return The tournament data for the given ids.
   * @throws TypeError
   * @throws GoError
   */
  def tournamentRecordsHaystack(id: String, ownerId: String, limit: js.UndefOr[Number] = js.undefined, cursor: js.UndefOr[String] = js.undefined, expiry: js.UndefOr[Number] = js.undefined): TournamentRecordList

  /**
   * Create a new group.
   *
   * @param userId      - The user ID to be associated as the group superadmin.
   * @param name        - Group name, must be set and unique.
   * @param creatorId   - Opt. The user ID to be associated as creator. If not set or null system user will be set.
   * @param lang        - Opt. Group language. Will default to 'en'.
   * @param description - Opt. Group description, use null to leave empty.
   * @param avatarURL   - Opt. URL to the group avatar, use null to leave empty.
   * @param open        - Opt. Whether the group is for anyone to join, or members will need to send invitations to join. Defaults to false.
   * @param metadata    - Opt. Custom information to store for this group, use null to leave empty.
   * @param limit       - Opt. Maximum number of members to have in the group. Defaults to 100.
   * @return An array of group objects.
   * @throws TypeError
   * @throws GoError
   */
  def groupCreate(userId: String, name: String, creatorId: js.UndefOr[String | Null] = js.undefined, lang: js.UndefOr[String | Null] = js.undefined, description: js.UndefOr[String | Null] = js.undefined, avatarURL: js.UndefOr[String | Null] = js.undefined, open: js.UndefOr[Boolean | Null] = js.undefined, metadata: js.UndefOr[js.Dictionary[js.Any] | Null] = js.undefined, limit: js.UndefOr[Number | Null] = js.undefined): Group

  /**
   * Update a group with various configuration settings.
   * The group which is updated can change some or all of its fields.
   *
   * @param groupId     - The group ID to update.
   * @param userId      - The user ID calling group Update. Use null to use system user.
   * @param name        - Group name, use null to not update.
   * @param creatorId   - The user ID to be associated as creator, use null to not update.
   * @param lang        - Group language, use null to not update.
   * @param description - Group description, use null to not update.
   * @param avatarURL   - URL to the group avatar, use null to not update.
   * @param open        - Whether the group is for anyone to join or not. Use null to not update.
   * @param metadata    - Custom information to store for this group. Use null to not update.
   * @param limit       - Maximum number of members to have in the group. Use null if field is not being updated.
   * @throws TypeError
   * @throws GoError
   */
  def groupUpdate(groupId: String, userId: String | Null, name: js.UndefOr[String | Null] = js.undefined, creatorId: js.UndefOr[String | Null] = js.undefined, lang: js.UndefOr[String | Null] = js.undefined, description: js.UndefOr[String | Null] = js.undefined, avatarURL: js.UndefOr[String | Null] = js.undefined, open: js.UndefOr[Boolean | Null] = js.undefined, metadata: js.UndefOr[js.Dictionary[js.Any] | Null] = js.undefined, limit: js.UndefOr[Number | Null] = js.undefined): Unit

  /**
   * Delete a group.
   *
   * @param groupID - The group ID to update.
   * @throws TypeError
   * @throws GoError
   */
  def groupDelete(groupID: String): Unit

  /**
   * Kick users from a group.
   *
   * @param groupID  - The group ID to update.
   * @param userIds  - Array of user IDs to be kicked from the group.
   * @param callerID - Opt. User ID mandating the operation to check for sufficient priviledges. Defaults to admin user if empty.
   * @throws TypeError
   * @throws GoError
   */
  def groupUsersKick(groupID: String, userIds: js.Array[String], callerID: js.UndefOr[String] = js.undefined): Unit

  /**
   * List all members, admins and superadmins which belong to a group.
   * This also list incoming join requests too.
   *
   * @param groupID - The group ID to update.
   * @param limit   - Opt. Max number of returned results. Defaults to 100.
   * @param state   - Opt. Filter users by their group state (0: Superadmin, 1: Admin, 2: Member, 3: Requested to join). Use undefined to return all states.
   * @param cursor  - Opt. A cursor used to fetch the next page when applicable.
   * @return A list of group members.
   * @throws TypeError
   * @throws GoError
   */
  def groupUsersList(groupID: String, limit: js.UndefOr[Number] = js.undefined, state: js.UndefOr[Number] = js.undefined, cursor: js.UndefOr[String] = js.undefined): GroupUserList

  /**
   * List groups, admins and superadmins which belong to a group.
   *
   * @param name    - Opt. Lookup group by name. '%' Suffix is supported for prefix match. Lookup by name is mutually exclusive to the remaining filters. Pass null to use other filters.
   * @param langTag - Opt. Filter results by language tag. Pass null to disregard filter.
   * @param open    - Opt. Filter groups by open or closes state. Pass null for either.
   * @param members - Opt. Filter results by an upper bound number of group members. Pass null to disregard filter.
   * @param limit   - Opt. Max number of returned results. Defaults to 100.
   * @param cursor  - Opt. A cursor used to fetch the next page when applicable.
   * @return A list of groups matching the filter criteria.
   * @throws TypeError
   * @throws GoError
   */
  def groupsList(name: js.UndefOr[String] = js.undefined, langTag: js.UndefOr[String] = js.undefined, open: js.UndefOr[Boolean] = js.undefined, members: js.UndefOr[Number] = js.undefined, limit: js.UndefOr[Number] = js.undefined, cursor: js.UndefOr[String] = js.undefined): GroupList

  /**
   * Get group data for a given number of random groups.
   *
   * @param count - Number of groups to retrieve.
   * @throws TypeError
   * @throws GoError
   */
  def groupsGetRandom(count: Number): js.Array[Group]

  /**
   * List all groups the user belongs to.
   *
   * @param userId - User ID.
   * @param limit  - Opt. Max number of returned results. Defaults to 100.
   * @param state  - Opt. Filter users by their group state (0: Superadmin, 1: Admin, 2: Member, 3: Requested to join). Use undefined to return all states.
   * @param cursor - Opt. A cursor used to fetch the next page when applicable.
   * @return A list of group members.
   * @throws TypeError
   * @throws GoError
   */
  def userGroupsList(userId: String, limit: js.UndefOr[Number] = js.undefined, state: js.UndefOr[Number] = js.undefined, cursor: js.UndefOr[String] = js.undefined): UserGroupList

  /**
   * List a user's friends.
   *
   * @param userId - User ID.
   * @param limit  - Opt. Max number of returned results. Defaults to 100.
   * @param state  - Opt. Filter users by their group state (friend(0), invite_sent(1), invite_received(2), blocked(3)). Use undefined to return all states.
   * @param cursor - Opt. A cursor used to fetch the next page when applicable.
   * @return A list of friends.
   * @throws TypeError
   * @throws GoError
   */
  def friendsList(userId: String, limit: js.UndefOr[Number] = js.undefined, state: js.UndefOr[Number] = js.undefined, cursor: js.UndefOr[String] = js.undefined): FriendList

  /**
   * Add friends to a user.
   *
   * @param userId    - User ID.
   * @param username  - Username.
   * @param ids       - The IDs of the users you want to add as friends.
   * @param usernames - The usernames of the users you want to add as friends.
   * @throws TypeError
   * @throws GoError
   */
  def friendsAdd(userId: String, username: String, ids: js.Array[String], usernames: js.Array[String]): FriendList

  /**
   * Delete friends from a user.
   *
   * @param userId    - User ID.
   * @param username  - Username.
   * @param ids       - The IDs of the users you want to delete as friends.
   * @param usernames - The usernames of the users you want to delete as friends.
   * @throws TypeError
   * @throws GoError
   */
  def friendsDelete(userId: String, username: String, ids: js.Array[String], usernames: js.Array[String]): FriendList

  /**
   * Block friends for a user.
   *
   * @param userId    - User ID.
   * @param username  - Username.
   * @param ids       - The IDs of the users you want to block as friends.
   * @param usernames - The usernames of the users you want to block as friends.
   * @throws TypeError
   * @throws GoError
   */
  def friendsBlock(userId: String, username: String, ids: js.Array[String], usernames: js.Array[String]): FriendList

  /**
   * Join a user to a group.
   *
   * @param groupID  - Group ID.
   * @param userId   - User ID to join the group.
   * @param username - Username of the user to join the group.
   * @throws TypeError
   * @throws GoError
   */
  def groupUserJoin(groupID: String, userId: String, username: String): Unit

  /**
   * Leave a user from a group.
   *
   * @param groupID  - Group ID.
   * @param userId   - User ID to join the group.
   * @param username - Username of the user to join the group.
   * @throws TypeError
   * @throws GoError
   */
  def groupUserLeave(groupID: String, userId: String, username: String): Unit

  /**
   * Add multiple users to a group.
   *
   * @param groupID  - Group ID.
   * @param userIds  - Array of userIds to add the group.
   * @param callerID - Opt. User ID mandating the operation to check for sufficient priviledges. Defaults to admin user if empty.
   * @throws TypeError
   * @throws GoError
   */
  def groupUsersAdd(groupID: String, userIds: js.Array[String], callerID: js.UndefOr[String] = js.undefined): Unit

  /**
   * Ban multiple users from a group.
   *
   * @param groupID  - Group ID.
   * @param userIds  - Array of userIds to ban from the group.
   * @param callerID - Opt. User ID mandating the operation to check for sufficient priviledges. Defaults to admin user if empty.
   * @throws TypeError
   * @throws GoError
   */
  def groupUsersBan(groupID: String, userIds: js.Array[String], callerID: js.UndefOr[String] = js.undefined): Unit

  /**
   * Promote users in a group.
   *
   * @param groupID  - Group ID.
   * @param userIds  - Array of userIds in the group to promote.
   * @param callerID - Opt. User ID mandating the operation to check for sufficient priviledges. Defaults to admin user if empty.
   * @throws TypeError
   * @throws GoError
   */
  def groupUsersPromote(groupID: String, userIds: js.Array[String], callerID: js.UndefOr[String] = js.undefined): Unit

  /**
   * Demote users in a group.
   *
   * @param groupID  - Group ID.
   * @param userIds  - Array of userIds in the group to demote.
   * @param callerID - Opt. User ID mandating the operation to check for sufficient priviledges. Defaults to admin user if empty.
   * @throws TypeError
   * @throws GoError
   */
  def groupUsersDemote(groupID: String, userIds: js.Array[String], callerID: js.UndefOr[String] = js.undefined): Unit

  /**
   * Fetch one or more groups by their ID.
   *
   * @param groupIDs - A set of strings of the ID for the groups to get.
   * @return An array of group objects.
   */
  def groupsGetId(groupIDs: js.Array[String]): js.Array[Group]

  /**
   * Read a file relative from the runtime path.
   *
   * @param relPath - Relative Path.
   * @return The content of the file as a string, if found.
   * @throws TypeError
   * @throws GoError
   */
  def fileRead(relPath: String): String

  /**
   * Validate an Apple receipt containing purchases.
   *
   * @param userID           - User ID.
   * @param receipt          - Apple receipt to validate.
   * @param persist          - Opt. Whether to persist the receipt validation. Defaults to true.
   * @param passwordOverride - Opt. Override the configured Apple Store Validation Password.
   * @return The result of the validated and stored purchases from the receipt.
   * @throws TypeError
   * @throws GoError
   */
  def purchaseValidateApple(userID: String, receipt: String, persist: js.UndefOr[Boolean] = js.undefined, passwordOverride: js.UndefOr[String] = js.undefined): ValidatePurchaseResponse

  /**
   * Validate a Google purchase payload.
   *
   * @param userID              - User ID.
   * @param purchase            - Google purchase payload to validate.
   * @param persist             - Opt. Whether to persist the receipt validation. Defaults to true.
   * @param clientEmailOverride - Opt. Override the configured Google Service Account client email.
   * @param privateKeyOverride  - Opt. Override the configured Google Service Account private key.
   * @return The result of the validated and stored purchases from the receipt.
   * @throws TypeError
   * @throws GoError
   */
  def purchaseValidateGoogle(userID: String, purchase: String, persist: js.UndefOr[Boolean] = js.undefined, clientEmailOverride: js.UndefOr[String] = js.undefined, privateKeyOverride: js.UndefOr[String] = js.undefined): ValidatePurchaseResponse

  /**
   * Validate a Huawei purchase payload.
   *
   * @param userID  - User ID.
   * @param receipt - Apple receipt to validate.
   * @param persist - Opt. Whether to persist the receipt validation. Defaults to true.
   * @return The result of the validated and stored purchases from the receipt.
   * @throws TypeError
   * @throws GoError
   */
  def purchaseValidateHuawei(userID: String, receipt: String, signature: String, persist: js.UndefOr[Boolean] = js.undefined): ValidatePurchaseResponse

  /**
   * Get a validated purchase data by transaction ID.
   *
   * @param transactionID - Transaction ID. For Google/Huawei this is the purchaseToken value of the purchase data.
   * @return The data of the validated and stored purchase.
   * @throws TypeError
   * @throws GoError
   */
  def purchaseGetByTransactionId(transactionID: String): ValidatedPurchaseOwner

  /**
   * List validated and stored purchases.
   *
   * @param userID - Opt. User ID.
   * @param limit  - Opt. Limit of results per page. Must be a value between 1 and 100.
   * @param cursor - Opt. A cursor used to fetch the next page when applicable.
   * @return A page of validated and stored purchases.
   * @throws TypeError
   * @throws GoError
   */
  def purchasesList(userID: js.UndefOr[String] = js.undefined, limit: js.UndefOr[Number] = js.undefined, cursor: js.UndefOr[String] = js.undefined): ValidatedPurchaseList

  /**
   * Validate an Apple receipt containing a subscription.
   *
   * @param userID           - User ID.
   * @param receipt          - Apple subscription receipt to validate.
   * @param persist          - Opt. Whether to persist the subscription validation. Defaults to true.
   * @param passwordOverride - Opt. Override the configured Apple Store Validation Password.
   * @return The result of the validated and stored purchases from the receipt.
   * @throws TypeError
   * @throws GoError
   */
  def subscriptionValidateApple(userID: String, receipt: String, persist: js.UndefOr[Boolean] = js.undefined, passwordOverride: js.UndefOr[String] = js.undefined): ValidateSubscriptionResponse

  /**
   * Validate a Google receipt containing a subscription.
   *
   * @param userID              - User ID.
   * @param subscription        - Google subscription payload to validate.
   * @param persist             - Opt. Whether to persist the subscription receipt validation. Defaults to true.
   * @param clientEmailOverride - Opt. Override the configured Google Service Account client email.
   * @param privateKeyOverride  - Opt. Override the configured Google Service Account private key.
   * @return The result of the validated and stored purchases from the receipt.
   * @throws TypeError
   * @throws GoError
   */
  def subscriptionValidateGoogle(userID: String, subscription: String, persist: js.UndefOr[Boolean] = js.undefined, clientEmailOverride: js.UndefOr[String] = js.undefined, privateKeyOverride: js.UndefOr[String] = js.undefined): ValidateSubscriptionResponse

  /**
   * Get a validated subscription data by product ID.
   *
   * @param userID    - User ID.
   * @param productID - Product ID. For Google this is the subscriptionToken value of the purchase data.
   * @return The data of the validated and stored purchase.
   * @throws TypeError
   * @throws GoError
   */
  def subscriptionGetByProductId(userID: String, productID: String): ValidatedPurchaseOwner

  /**
   * Send channel message.
   *
   * @param channelId      - Channel ID.
   * @param content        - Message content.
   * @param senderId       - Opt. Message sender ID.
   * @param senderUsername - Opt. Sender username. Defaults to system user.
   * @param persist        - Opt. Store message. Defaults to true.
   * @return Ack of sent message.
   * @throws TypeError
   * @throws GoError
   */
  def channelMessageSend(channelId: String, content: js.UndefOr[js.Dictionary[js.Any]] = js.undefined, senderId: js.UndefOr[String] = js.undefined, senderUsername: js.UndefOr[String] = js.undefined, persist: js.UndefOr[Boolean] = js.undefined): ChannelMessageSendAck

  /**
   * Update channel message.
   *
   * @param channelId      - Channel ID.
   * @param messageId      - Message ID of message to be updated.
   * @param content        - Message content.
   * @param senderId       - Opt. Message sender ID.
   * @param senderUsername - Opt. Sender username. Defaults to system user.
   * @param persist        - Opt. Store message. Defaults to true.
   * @return Ack of sent message.
   * @throws TypeError
   * @throws GoError
   */
  def channelMessageUpdate(channelId: String, messageId: String, content: js.UndefOr[js.Dictionary[js.Any]] = js.undefined, senderId: js.UndefOr[String] = js.undefined, senderUsername: js.UndefOr[String] = js.undefined, persist: js.UndefOr[Boolean] = js.undefined): ChannelMessageSendAck

  /**
   * List channel messages.
   *
   * @param channelId - Channel ID.
   * @param limit     - The number of messages to return per page.
   * @param forward   - Whether to list messages from oldest to newest, or newest to oldest.
   * @param cursor    - Opt. Pagination cursor.
   * @return List of channel messages.
   * @throws TypeError
   * @throws GoError
   */
  def channelMessagesList(channelId: String, limit: js.UndefOr[Number] = js.undefined, forward: js.UndefOr[Boolean] = js.undefined, cursor: js.UndefOr[String] = js.undefined): ChannelMessageList

  /**
   * Send channel message.
   *
   * @param sender - The user ID of the sender.
   * @param target - The user ID to DM with, group ID to chat with, or room channel name to join.
   * @param type   - Channel type.
   * @return The channelId.
   * @throws TypeError
   * @throws GoError
   */
  def channelIdBuild(sender: String, target: String, chanType: ChanType.Type): String

  /**
   * Parses a CRON expression and a timestamp in UTC seconds, and returns the next matching timestamp in UTC seconds.
   *
   * @param cron      - The cron expression.
   * @param timestamp - UTC unix seconds timestamp.
   * @return The next cron matching timestamp in UTC seconds.
   * @throws TypeError
   * @throws GoError
   */
  def cronNext(cron: String, timestamp: Number): Number
}

/**
 * The start function for Nakama to initialize the server logic.
 */
trait InitModule extends js.Function {
  /**
   * Executed at server startup.
   *
   * This function executed will block the start up sequence of the game server. You must use
   * care to limit the compute time of logic run in this function.
   *
   * @param ctx         - The context of the execution.
   * @param logger      - The server logger.
   * @param nk          - The Nakama server APIs.
   * @param initializer - The injector to initialize features in the game server.
   */
  def apply(ctx: Context, logger: Logger, nk: Nakama, initializer: Initializer): Unit
}
