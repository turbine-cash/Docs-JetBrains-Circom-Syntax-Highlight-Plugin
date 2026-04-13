# Circom Plugin Documentation (Starlight)

Documentation site for the JetBrains Circom Language Support plugin. Deployed to Cloudflare Pages.

## Tech Stack

- **Framework**: Astro + Starlight
- **Plugins**: @pasqal-io/starlight-client-mermaid (diagrams)
- **Deploy**: Cloudflare Pages (static)
- **Styling**: Custom CSS with orange theme (#F97315)
- **Code Highlighting**: Custom Circom TextMate grammar for Shiki

## Commands

```bash
npm run dev      # Start dev server (localhost:4321)
npm run build    # Build to ./dist
npm run preview  # Preview build locally
npm run deploy   # Build and deploy to Cloudflare
```

## Directory Structure

```
src/
├── assets/              # Images (logo.png)
├── components/          # Custom Astro components
│   ├── Header.astro     # Custom header with Get Plugin button + social icons
│   ├── Sidebar.astro    # Sidebar with Get Plugin link
│   ├── PageFrame.astro  # Layout with TOC highlighting
│   └── PageTitle.astro  # Title without breadcrumbs
├── content/docs/        # MDX documentation files
│   ├── index.mdx        # Redirects to /introduction/
│   ├── introduction.mdx # Main landing page
│   ├── installation.mdx
│   ├── features/        # Feature documentation
│   │   ├── syntax-highlighting.mdx
│   │   ├── editor-features.mdx
│   │   ├── navigation.mdx
│   │   ├── find-usages.mdx
│   │   ├── structure-view.mdx
│   │   ├── refactoring.mdx
│   │   └── color-settings.mdx
│   ├── keyboard-shortcuts.mdx
│   └── troubleshooting.mdx
├── grammars/
│   └── circom.tmLanguage.json  # Circom syntax highlighting for code blocks
└── styles/
    └── custom.css       # Theme overrides
public/
├── images/              # Static images for docs
└── favicon.png
screenshot-tests/        # IntelliJ RemoteRobot screenshot capture tests
├── build.gradle.kts
├── fixtures/            # Sample .circom files
└── src/test/kotlin/     # Screenshot test classes
```

## Adding New Documentation

### 1. Create MDX File

Add file to `src/content/docs/` following the structure above.

### 2. Add Frontmatter

```mdx
---
title: "Page Title"
description: "Brief description for SEO"
---
```

### 3. Update Sidebar

Edit `astro.config.mjs` → `sidebar` array:

```javascript
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
    ],
  },
],
```

### 4. Use Components

```mdx
import { Aside, Steps, Card, CardGrid, LinkCard } from '@astrojs/starlight/components';

<Aside type="tip">
Pro tip content here
</Aside>

<CardGrid>
  <LinkCard title="Card 1" href="/path1" description="Description" />
  <LinkCard title="Card 2" href="/path2" description="Description" />
</CardGrid>
```

## Circom Code Blocks

Code blocks with `circom` language tag get syntax highlighting:

```circom
pragma circom 2.0.0;

template Multiplier() {
    signal input a;
    signal input b;
    signal output out;
    out <== a * b;
}
```

The grammar is defined in `src/grammars/circom.tmLanguage.json` and loaded in `astro.config.mjs`.

## Links

- **Plugin Marketplace**: https://plugins.jetbrains.com/plugin/29751-circom-language-support
- **GitHub**: https://github.com/turbine-cash/JetBrains-Circom-Syntax-Highlight-Plugin

## Gotchas

1. **Mermaid diagrams**: Requires `@pasqal-io/starlight-client-mermaid` plugin
2. **Logo path**: Must reference `./src/assets/`, not `./public/`
3. **Image imports in MDX**: Use relative paths from the MDX file location
4. **Circom grammar**: Loaded via `expressiveCode.shiki.langs` in astro.config.mjs
