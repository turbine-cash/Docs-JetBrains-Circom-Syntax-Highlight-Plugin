// @ts-check
import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';
import starlightClientMermaid from '@pasqal-io/starlight-client-mermaid';
import cloudflare from '@astrojs/cloudflare';
import { readFileSync } from 'fs';

const circomGrammar = JSON.parse(
	readFileSync('./src/grammars/circom.tmLanguage.json', 'utf-8')
);

export default defineConfig({
	output: 'static',
	integrations: [
		starlight({
			plugins: [
				starlightClientMermaid(),
			],
			title: 'Circom Language Support',
			logo: {
				src: './src/assets/logo.png',
				replacesTitle: true,
			},
			tableOfContents: { minHeadingLevel: 2, maxHeadingLevel: 3 },
			favicon: '/favicon.png',
			customCss: ['./src/styles/custom.css'],
			social: [
				{ icon: 'github', label: 'GitHub', href: 'https://github.com/ZKLSOL/JetBrains-Circom-Syntax-Highlight-Plugin' },
			],
			components: {
				Header: './src/components/Header.astro',
				PageTitle: './src/components/PageTitle.astro',
				PageFrame: './src/components/PageFrame.astro',
			},
			defaultLocale: 'root',
			locales: {
				root: {
					label: 'English',
					lang: 'en',
				},
			},
			expressiveCode: {
				shiki: {
					langs: [circomGrammar],
				},
			},
			sidebar: [
				{
					label: 'Getting Started',
					items: [
						{ label: 'Introduction', slug: 'introduction' },
						{ label: 'Installation', slug: 'installation' },
					],
				},
				{
					label: 'Features',
					items: [
						{ label: 'Syntax Highlighting', slug: 'features/syntax-highlighting' },
						{ label: 'Editor Features', slug: 'features/editor-features' },
						{ label: 'Navigation', slug: 'features/navigation' },
						{ label: 'Find Usages', slug: 'features/find-usages' },
						{ label: 'Structure View', slug: 'features/structure-view' },
						{ label: 'Refactoring', slug: 'features/refactoring' },
						{ label: 'Color Settings', slug: 'features/color-settings' },
					],
				},
				{
					label: 'Reference',
					items: [
						{ label: 'Keyboard Shortcuts', slug: 'keyboard-shortcuts' },
						{ label: 'Troubleshooting', slug: 'troubleshooting' },
						{ label: 'Changelog', link: 'https://github.com/ZKLSOL/JetBrains-Circom-Syntax-Highlight-Plugin/releases' },
					],
				},
			],
		}),
	],
	adapter: cloudflare(),
});
