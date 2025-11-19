package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadContentServerSide;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.model.OperationStatus;
import ai.gebo.system.ingestion.GeboIngestionException;

public interface IGChatStorageAreaService {
	/************************************************************************************************
	 * Get the base path for a user session
	 * 
	 * @param context
	 * @return
	 * @throws IOException
	 */
	public Path getSessionPath(GUserChatContext context) throws IOException;

	/***************************************************************************************************
	 * adds the file to ths storage area for the actual session and ingests it
	 * saving its contents locally
	 * 
	 * @param userSessionCode
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws GeboContentHandlerSystemException 
	 * @throws GeboIngestionException 
	 */
	public UserUploadContentServerSide addUploadedFile(String userSessionCode, MultipartFile file) throws IOException, GeboContentHandlerSystemException, GeboIngestionException;

	/*****************************************************************************************************
	 * Deletes the file content
	 * 
	 * @param ss
	 * @throws IOException
	 */
	public void deleteUploadedFile(UserUploadContentServerSide ss) throws IOException;

	/*****************************************************************************************************
	 * Delete full session contents
	 * 
	 * @param context
	 * @throws IOException
	 */
	public void deleteSessionContents(GUserChatContext context) throws IOException;

	/******************************************************************************************************
	 * Gets the phisical path of the content
	 * 
	 * @param ss
	 * @return
	 * @throws IOException
	 */
	public Path getUploadedFilePath(UserUploadContentServerSide ss) throws IOException;

	/******************************************************************************************************
	 * Get ingested contents for the user uploaded content
	 * 
	 * @param ss
	 * @return
	 * @throws IOException
	 */
	public List<Document> getIngestedContentsOf(UserUploadContentServerSide ss) throws IOException;

	public OperationStatus<List<UserUploadedContent>> deleteUploadedContents(String userSessionCode, List<String> id);

	public InputStream getContent(UserUploadedContent content) throws IOException;

}
