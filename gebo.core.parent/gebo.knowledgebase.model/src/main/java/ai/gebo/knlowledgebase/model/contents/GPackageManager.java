/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.contents;

/**
 * AI generated comments
 * Enum representing different types of package managers.
 * Each constant corresponds to a popular package management system used in various programming environments.
 */
public enum GPackageManager {
	maven, // Apache Maven, used primarily for Java projects
	gradle, // Gradle, a flexible build automation system also used for Java
	npm, // Node Package Manager, used for JavaScript projects
	cargo, // Cargo, the Rust package manager
	cocoa, // CocoaPods, used for iOS development
	pear, // PHP Extension and Application Repository
	pip, // Python Package Index
	sbt, // Scala Build Tool
	yarn, // Yarn, another JavaScript package manager
	ivy, // Apache Ivy, a dependency manager
	nuget // NuGet, used for .NET
}