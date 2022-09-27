import React from 'react';
import ReactMarkdown from 'react-markdown';
import rehypeRaw from 'rehype-raw';
// import MdEditor from 'md-editor-rt'
// import 'md-editor-rt/lib/style.css';


export type MarkdownProps = {
  content?: string;
  isHtml?: boolean;
};
const Markdown: React.FC<MarkdownProps> = (props: MarkdownProps) => {
  const {
    content,
    isHtml = true,
  } = props;


  return (
    <>
      {/*<MdEditor*/}
      {/*  style={{height:"100%"}}*/}
      {/*  modelValue={content || ''}*/}
      {/*  // previewOnly={true}*/}
      {/*  // onChange={onChange}*/}
      {/*/>*/}

      <ReactMarkdown rehypePlugins={isHtml ? [rehypeRaw] : []}
                     components={{
                       img(props) {
                         return <img {...props} style={{maxWidth: '100%'}}/>
                       }
                     }}
      >
        {content || ''}
      </ReactMarkdown>
    </>
  );
};

export default Markdown;
